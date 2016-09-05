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

        String Databese_Path = "../neo4j-nexus/data/graph.db";

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


        /**
         * {ands_group} --> {funder}
         */

        Cypher_Query = "match (c:grant) \n" +

                "where c.funder is null with c limit 10000 \n" +

                "set c.funder = c.ands_group \n" +

                "remove c.ands_group";

        for (int i = 0; i < 500; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
        }

        /**
         * {nhmrc_id,arc_id, riginal_key}-->{local_id}
         */

        Cypher_Query = "match (c:grant)\n" +

                "where c.nhmrc_id is not null and c.local_id is null and riginal_key is null\n" +

                "set c.local_id = c.nhmric_id\n" +

                "remove c.nhmric_id";

        ExecutionResult execResult = execEngine.execute(Cypher_Query);
        String results = execResult.dumpToString();
        System.out.println(results);


        Cypher_Query = "match (c:grant)\n" +

                "where c.arc_id is not null and c.nhmrc_id is null and c.riginal_key is null\n" +

                "set c.local_id = c.arc_id";

        ExecutionResult execResult = execEngine.execute(Cypher_Query);
        String results = execResult.dumpToString();
        System.out.println(results);

        Cypher_Query = "match (c:grant)\n" +

                "where c.riginal_key is not null and c.nhmrc_id is null and c.arc_id is null\n" +

                "set c.local_id = c.riginal_key";

        ExecutionResult execResult = execEngine.execute(Cypher_Query);
        String results = execResult.dumpToString();
        System.out.println(results);

    }

}