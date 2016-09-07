import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.logging.Log;
import org.neo4j.logging.LogProvider;

/**
 * Created by wangkun on 7/09/2016.
 * this class is used for fixing bugs in Publication
 */
public class Publication2 {

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
         * 1. {ands_group} --> {group}
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

        for (int i = 0; i < 2; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
            if (results.contains("No data returned, and nothing was changed.")) {
                break;
            }
        }


        /**
         * 2. {publication} --> {p.publication_year}
         *
         */



        Cypher_Query = "match (p:publication) where HAS(p.publication) with p limit 10000 set p.publication_year = p.publication remove p.publication";

        for (int i = 0; i < 411; i++) {
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);
            if (results.contains("No data returned, and nothing was changed.")) {
                break;
            }
        }

    }
}
