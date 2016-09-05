import org.neo4j.cypher.ExecutionEngine;

import org.neo4j.cypher.ExecutionResult;

import org.neo4j.graphdb.GraphDatabaseService;

import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import org.neo4j.logging.Log;

import org.neo4j.logging.LogProvider;

/**
 * Created by Andy on 2016-09-02.
 * My site: http://ukiyoe.in
 */

public class Grant {

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
         * {ands_group} --> {funder}
         */

        Cypher_Query = "match (c:grant) \n" +

                "where c.funder is null with c limit 10000 \n" +

                "set c.funder = c.ands_group \n" +

                "remove c.ands_group";

        for (int i = 0; i < 10; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * {nhmrc_id,arc_id, riginal_key}-->{local_id}
         * Use the properties in the order of priority:  nhmrc_id (first) ,arc_id (second) , riginal_key (last)
         */

        Cypher_Query = "match (c:grant)\n" +

                "where c.nhmrc_id is not null\n" +

                "set c.local_id = c.nhmrc_id\n" +

                "remove c.nhmrc_id\n";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }


        Cypher_Query = "match (c:grant)\n" +

                "where c.nhmrc_id is null and c.arc_id is not null\n" +

                "set c.local_id = c.arc_id\n" +

                "remove c.arc_id";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        Cypher_Query = "match (c:grant)\n" +

                "where c.nhmrc_id is null and c.arc_id is null and c.riginal_key is not null\n" +

                "set c.local_id = c.riginal_key\n" +

                "remove c.riginal_key";

        for (int i = 0; i < 1; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }
    }
}