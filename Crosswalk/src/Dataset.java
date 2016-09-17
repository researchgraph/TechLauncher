import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.logging.Log;
import org.neo4j.logging.LogProvider;

/**
 * Created by wangkun on 7/09/2016.
 */
public class Dataset {

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
         * 1. {ands_group --> group}
         * 43354
         */

        Cypher_Query = "match (c:dataset)\n" +
                "where c.ands_group is not null with c limit 10000\n" +
                "set c.group = c.ands_group\n" +
                "remove c.ands_group";

        run(execEngine, Cypher_Query);


        /**
         * 2. {node_source --> source}
         * 94763
         */

        Cypher_Query = "match (c:dataset) \n" +
                "where c.node_source is not null with c limit 10000 \n" +
                "set c.source = c.node_source \n" +
                "remove c.node_source";

        run(execEngine, Cypher_Query);



        /**
         * 3. {local_id, original_key, doi} --> {local_id} // Use the properties in the order of priority: local_id, original_key, doi
         * 58978 + 51423
         */

        Cypher_Query = "match (c:dataset)\n" +
                "where c.original_key is not null and c.local_id is null\n" +
                "set c.local_id = c.original_key\n" +
                "remove c.original_key";

        run(execEngine, Cypher_Query);

        Cypher_Query = "match (c:dataset)\n" +
                "where c.doi is not null and c.local_id is null\n" +
                "set c.local_id = c.doi\n" +
                "remove c.doi";

        run(execEngine, Cypher_Query);


        /**
         * 4 create {url}
         * 94763
         */

        Cypher_Query = "match (c:dataset) \n" +
                "where c.url is null with c limit 10000 \n" +
                "set c.url = c. key";

        run(execEngine, Cypher_Query);


        /**
         * 5. create {last_update}
         * 94763
         */

        Cypher_Query = "match (c:dataset) \n" +
                "where c.last_update is null with c limit 10000 \n" +
                "set c.last_update  = 'April 2016: 21:47:57'";

        run(execEngine, Cypher_Query);


        /**
         * 6. {published_date} --> {publication_year}
         * 9739
         *
         * [2014, 2011] bJ8qYJHEPXw5qPQtV1L1ocG6IJcXdGZegXv5w3zcQW86BPpnH1eV
         * [2015-03-02T08:00:00Z, 2014-01-01T08:00:00Z]	epubs.scu.edu.au/5353481025351505356575310254
         * [2015-03-07T08:00:00Z, 2014-01-01T08:00:00Z]	epubs.scu.edu.au/535348102535448565450485597
         * [2015-03-07T08:00:00Z, 2014-01-01T08:00:00Z]	epubs.scu.edu.au/108108101991161051111101154752
         * [2011-07-28T07:00:00Z, 2014-12-02T08:00:00Z]	epubs.scu.edu.au/3534855561029910298571005398
         * [2015-01-01T08:00:00Z, 2015-09-03T07:00:00Z]	epubs.scu.edu.au/31019910157101101541021019851
         *
         */

        String abnormalData = "[2014,2011] bJ8qYJHEPXw5qPQtV1L1ocG6IJcXdGZegXv5w3zcQW86BPpnH1eV " +
                "[2015-03-02T08:00:00Z,2014-01-01T08:00:00Z] epubs.scu.edu.au/5353481025351505356575310254 " +
                "[2015-03-07T08:00:00Z,2014-01-01T08:00:00Z] epubs.scu.edu.au/535348102535448565450485597 " +
                "[2015-03-07T08:00:00Z,2014-01-01T08:00:00Z] epubs.scu.edu.au/108108101991161051111101154752 " +
                "[2011-07-28T07:00:00Z,2014-12-02T08:00:00Z] epubs.scu.edu.au/3534855561029910298571005398 " +
                "[2015-01-01T08:00:00Z,2015-09-03T07:00:00Z] epubs.scu.edu.au/31019910157101101541021019851";

        String[] t = abnormalData.split(" ");
        for (int i = 0; i < 6;i++) {
            Cypher_Query = "MATCH (n:dataset) where n.local_id = '" +
                    t[2*i+1] +
                    "' set n.publication_year = '" +
                    t[2*i].substring(1,5) +
                    "' remove n.published_date";
            ExecutionResult execResult = execEngine.execute(Cypher_Query);
            String results = execResult.dumpToString();
            System.out.println(results);        }



        Cypher_Query = "match (c:dataset) \n" +
                "where c.published_date is not null with c limit 10000\n" +
                "set c.publication_year = substring(str(c.published_date), 1, 4)\n" +
                "remove c.published_date";

        run(execEngine, Cypher_Query);



    }

    private static void run(ExecutionEngine execEngine, String cypher_query) {
        for (int i = 0; i < 11; i++) {
            ExecutionResult execResult = execEngine.execute(cypher_query);
            String results = execResult.dumpToString();
            System.out.println(results);
            if (results.contains("No data returned, and nothing was changed.")) {
                break;
            }
        }
    }
}
