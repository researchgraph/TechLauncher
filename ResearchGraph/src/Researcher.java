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

        String Databese_Path = "C:\\neo4j-nexus\\neo4j-nexus\\data\\graph.db";

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

                "where c.group is null \n" +

                "set c.group = c.ands_group \n" +

                "remove c.ands_group";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * {title} --> {full_name}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.full_name is null and c.title is not null \n" +

                "set c.full_name = c.title \n" +

                "remove c.title";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * {node_source} --> {node}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.node is null \n" +

                "set c.node = c.node_source \n" +

                "remove c.node_source";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * {key} --> {url}
         */

        Cypher_Query = "match (c:researcher) \n" +

                "where c.url is null \n" +

                "set c.url = c.key";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * {local_id, original_key, orcid, key} --> {local_id}
         * Replace with these fields in the order of priority {local_id, original_key, orcid, key}, that is first local_id,
         * but if it does not exist then original_key, if that one also does not exist then orcid and finally use the value of the key
         */

        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is not null\n" +

                "remove c.orcid\n" +

                "remove c.original_key\n" +

                "remove c.key\n"
        ;

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }


        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is null and c.original_key is not null\n" +

                "set c.local_id = c.original_key\n" +

                "remove c.original_key\n";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is null and c.original_key is null and c.orcid is not null\n" +

                "set c.local_id = c.orcid\n" +

                "remove c.orcid\n";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        Cypher_Query = "match (c:researcher)\n" +

                "where c.local_id is null and c.orcid is null and c.original_key is null and c.url is not null\n" +

                "set c.local_id = c.url\n";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }
    }
}
