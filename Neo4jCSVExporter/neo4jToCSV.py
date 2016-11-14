from neo4j.v1 import GraphDatabase, basic_auth
import csv


class Database(object):

    def __init__(self,data_name, password):
        super(Database, self).__init__()
        self.driver = GraphDatabase.driver(data_name, auth=basic_auth("neo4j", password))
    def get_labels(self):
        session = self.driver.session()
        result = session.run("MATCH(n) RETURN distinct labels(n)")
        session.close()
        return result
    def count_nodes(self,label_list):
        label_result_dict={}
        session = self.driver.session()
        for label in label_list:
            result = session.run("MATCH(n:%s) RETURN count(n)"%label)
            # print result
            label_result_dict[label]=result
        session.close()
        # print label_result_dict
        return label_result_dict

    def get_distinct_labels(self):
        session = self.driver.session()
        result = session.run("MATCH (n) WITH DISTINCT labels(n) AS labels "
                     "UNWIND labels AS label "
                     "RETURN DISTINCT label "
                     "ORDER BY label")
        session.close()
        return result

    def match_ands_grant(self):
        session = self.driver.session()
        result = session.run("match (n1:ands)-[x]-(n2:grant) return id(n1), x, id(n2)")
        session.close()
        return result
    def match_two_labels_2_degree(self,label1,label2):
        session = self.driver.session()
        result = session.run("match (n1:%s)--(t)-[x]-(k)--(n2:%s) return  id(n1), id(n2)" % (label1, label2))
        session.close()
        return result

    def match_two_labels(self,label1,label2):
        session = self.driver.session()
        result = session.run("match (n1:%s)-[x]-(n2:%s) return id(n1), x, id(n2)" % (label1, label2))
        session.close()
        return result

    def match_two_labels_query_generate(self,label1,label2):
        result = "match (n1:%s)-[x]-(n2:%s) return n1.key, x, n2.key" % (label1, label2)
        return result

    def match_two_labels_2degree_query_generate(self,label1,label2):
        result = "match (n1:%s)--(t)-[x]-(k)--(n2:%s) return  n1.key, x, n2.key" % (label1, label2)
        return result

class CSV_operate(object):
    def __init__(self):
        super(CSV_operate,self).__init__()

    def export(self,filename,result,line1,line2):
        csvfile = open(filename, 'wb')
        spamwriter = csv.writer(csvfile, delimiter=',',
                            quotechar='|', quoting=csv.QUOTE_MINIMAL)
        spamwriter.writerow([line1,line2])
        for record in result:
            spamwriter.writerow([record["id(n1)"],record["id(n2)"]])

    def export_nodes_info(self,filename,label_result_dict):
        csvfile = open(filename, 'wb')
        spamwriter = csv.writer(csvfile, delimiter=',',
                                quotechar='|', quoting=csv.QUOTE_MINIMAL)
        spamwriter.writerow(["label_name", "node_number"])
        for record in label_result_dict:
            spamwriter.writerow([record[0], record[1]])

if __name__ == '__main__':
    # Remember to change the file name.
    curr_database = Database("bolt://localhost","1234")
    query_file = open("queries.cql","wb")
    query_file2 = open("queries_2degree.cql", "wb")
    label_count_query_file = open("label_count.cql","wb")
    my_CSV_operator = CSV_operate()
    labels = curr_database.get_distinct_labels()
    labels2 = labels
    label_list = []
    for label in labels:
        label_list.append(label["label"])
    print label_list
    for label1 in label_list:
        # print label1
        label_node_count_query = "import-cypher -o label_node_query.csv match (n:%s) return count(n)"%label1
        label_count_query_file.write(label_node_count_query)
        label_count_query_file.write("\n")
        for label2 in label_list:
            if label1 is not label2:
                print "2:"+label2
            result1= curr_database.match_two_labels_query_generate(label1,label2)
            result2 = curr_database.match_two_labels_2degree_query_generate(label1,label2)
            filename1=label1+"_"+label2+"_degree1.csv"
            filename2 = label1+"_"+label2+"_degree2.csv"
            shell_query1 = "import-cypher -o "+filename1+" "+result1
            shell_query2 = "import-cypher -o "+filename2+" "+result2
            query_file.write(shell_query1)
            query_file2.write(shell_query2)
            query_file.write("\n")
            query_file2.write("\n")
            #my_CSV_operator.export(filename,result,label1,label2)

