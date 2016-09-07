import org.neo4j.cypher.ExecutionEngine;
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

        

    }
}
