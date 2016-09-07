import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.logging.Log;
import org.neo4j.logging.LogProvider;

/**
 * Created by wangkun on 31/08/2016.
 */
public class Publication {

    public static void main(String[] args) {

        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();

        String Databese_Path = "/Users/wangkun/Desktop/ResearchGraph/neo4j-nexus/data/graph.db";

        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(Databese_Path);
        ExecutionEngine execEngine = new ExecutionEngine(graphDb, new LogProvider() {
            @Override
            public Log getLog(Class aClass) {
                return null;
            }

            @Override
            public Log getLog(String s) {
                return null;
            }
        });


        String Cypher_Query = "";

        /**
         * 1. {node_source} --> {source}
         * 4102316 nodes need to change
         * Cypher Query:
         * match (c:publication)
         * where c.source is null
         * return count (c)
         */


        Cypher_Query = "match (c:publication) \n" +
                "where c.source is null with c limit 10000 \n" +
                "set c.source = c.node_source \n" +
                "remove c.node_source";

        for (int i = 0; i < 411; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * 2.{authors} --> {authors_list}
         * 3767904 nodes need to change
         * Cypher Query:
         * match (c:publication)
         * where c.authors is not null
         * return count (c)
         */


        Cypher_Query = "match (c:publication) \n" +
                "where c.authors is not null with c limit 10000 \n" +
                "set c.authors_list = c.authors \n" +
                "remove c.authors";

        for (int i = 0; i < 377; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * 3. create {url}
         * 4102316 nodes need to change
         * Cypher Query:
         * match (c:publication)
         * where c.url is null
         * return count (c)
         */


        Cypher_Query = "match (c:publication) \n" +
                "where c.url is null with c limit 10000 \n" +
                "set c.url = c. key";

        for (int i = 0; i < 411; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }


        //Have corrected the mistake
        /**
         * 4. {published_date} --> {publication} // Just pick the year component of the published_date value
         * 4069780 nodes to change
         * Cypher Query:
         * match (c:publication)
         * where c.published_date is not null
         * return count (c)
         */

        Cypher_Query = "match (c:publication) \n" +
                "where c.published_date is not null with c limit 10000 \n" +
                "set c.publication_year = substring(c.published_date, 0, 4) \n" +
                "remove c.published_date";

        for (int i = 0; i < 407; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * 5. create {last_update} // default value=11 April 2016: 21:47:57
         * 4102316 nodes to change
         * Cypher Query:
         * match (c:publication)
         * where c.last_update is null
         * return count (c)
         */


        Cypher_Query = "match (c:publication) \n" +
                "where c.last_update is null with c limit 10000 \n" +
                "set c.last_update  = 'April 2016: 21:47:57'";

        for (int i = 0; i < 411; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * 6. {local_id, inspire_id, doi} --> {local_id} // Use the properties in the order of priority: local_id, inspire_id, doi
         */

        /**
         * step 6.1: //find nodes which have inspire_id but have no local_id, set local_id = inspire_id then remove inspire_id
         * 543 nodes to change
         * match (c:publication)
         * where c.inspire_id is not null and c.local_id is null
         * return count (c)
         */

        Cypher_Query = "match (c:publication)\n" +
                "where c.inspire_id is not null and c.local_id is null\n" +
                "set c.local_id = c.inspire_id\n" +
                "remove c.inspire_id";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * step 6.2: //find nodes which have doi but have no local_id, set local_id = doi
         * 11789 nodes to change
         * Cypher Query:
         * match (c:publication)
         * where c.doi is not null and c.local_id is null
         * return count (c)
         */

        Cypher_Query = "match (c:publication)\n" +
                "where c.doi is not null and c.local_id is null\n" +
                "set c.local_id = c.doi";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        //This part is missing in the first converting

        /**
         * *7. {ands_group} --> {group}
         * 17872 nodes need to change
         * Cypher Query:
         * match (p:publication)
         * where HAS(p.ands_group)
         * return count(p.key)
         */


        Cypher_Query = "match (c:publication)\n" +
                "where c.ands_group is not null with c limit 10000\n" +
                "set c.group = c.ands_group\n" +
                "remove c.ands_group";

        for (int i = 0; i < 3; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
            if (results.contains("No data returned, and nothing was changed.")) {
                break;
            }
        }




    }
}
