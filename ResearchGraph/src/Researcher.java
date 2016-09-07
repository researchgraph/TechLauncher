import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.logging.Log;
import org.neo4j.logging.LogProvider;

/**
 * Created by Yunxiang Xiang on 2016-09-06.
 * My site: http://ukiyoe.in
 */
public class Researcher {

    public static void main(String[] args) {

        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();

        String Cypher_Query = "";

        /*

         locate the database address

         notice must end with graph.db

        * */

        String Databese_Path = "/Users/wangkun/Desktop/ResearchGraph/neo4j-nexus/data/graph.db";

        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(Databese_Path);

        ExecutionEngine execEngine = new ExecutionEngine(graphDb, new LogProvider() {
            public Log getLog(Class aClass) {
                return null;
            }

            public Log getLog(String s) {
                return null;
            }
        });

        /**
         * {ands_group} --> {group}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.group is null with c limit 10000\n" +

                "set c.group = c.ands_group \n" +

                "remove c.ands_group";

        run(execEngine, Cypher_Query);


        /**
         * {title} --> {full_name}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.full_name is null and c.title is not null with c limit 10000\n" +

                "set c.full_name = c.title \n" +

                "remove c.title";

        run(execEngine, Cypher_Query);


        /**
         * {node_source} --> {source}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.source is null with c limit 10000\n" +

                "set c.source = c.node_source \n" +

                "remove c.node_source";

        run(execEngine, Cypher_Query);

        /**
         * {scopus_id} --> {scopus_author_id}
         */


        Cypher_Query = "match (c:researcher)\n" +

                "where c.scopus_author_id is null and c.scopus_id is not null with c limit 10000\n" +

                "set c.scopus_author_id = c.scopus_id\n" +

                "remove c.scopus_id\n";

        run(execEngine, Cypher_Query);


        /**
         * {key} --> {url}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.url is null with c limit 10000\n" +

                "set c.url = c.key";

        run(execEngine, Cypher_Query);


        /**
         * {local_id, original_key, orcid, key} --> {local_id}
         * Replace with these fields in the order of priority {local_id, original_key, orcid, key}, that is first local_id,
         * but if it does not exist then original_key, if that one also does not exist then orcid and finally use the value of the key
         */

        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is not null with c limit 10000\n" +

                "remove c.orcid\n" +

                "remove c.original_key\n" +

                "remove c.key\n"
        ;

        run(execEngine, Cypher_Query);



        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is null and c.original_key is not null with c limit 10000\n" +

                "set c.local_id = c.original_key\n" +

                "remove c.original_key\n";

        run(execEngine, Cypher_Query);






        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is null and c.original_key is null and c.orcid is not null with c limit 10000\n" +

                "set c.local_id = c.orcid\n" +

                "remove c.orcid\n";

        run(execEngine, Cypher_Query);


        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is null and c.orcid is null and c.original_key is null and c.url is not null with c limit 10000\n" +

                "set c.local_id = c.url\n";

        run(execEngine, Cypher_Query);




    }

    private static void run(ExecutionEngine execEngine, String cypher_query) {
        for (int i = 0; i < 29; i++) {
            ExecutionResult execResult = execEngine.execute(cypher_query);
            String results = execResult.dumpToString();
            System.out.println(results);
            if (results.contains("No data returned, and nothing was changed.")) {
                break;
            }
        }
    }
}
