//import org.neo4j.cypher.ExecutionEngine;
//import org.neo4j.cypher.ExecutionResult;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.logging.Log;
//import org.neo4j.logging.LogProvider;
//
///**
// * Created by wangkun on 26/08/2016.
// */
//public class MySecondAttempt {
//    public static void main(String[] args) {
//
//        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
//
//        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase("/Users/wangkun/Desktop/ResearchGraph/neo4j-nexus/data/graph.db");
//        ExecutionEngine execEngine = new ExecutionEngine(graphDb, new LogProvider() {
//            @Override
//            public Log getLog(Class aClass) {
//                return null;
//            }
//
//            @Override
//            public Log getLog(String s) {
//                return null;
//            }
//        });
//
//        ExecutionResult execResult = execEngine.execute("MATCH (n:publication) RETURN n LIMIT 25");
//        String results = execResult.dumpToString();
//        System.out.println(results);
//    }
//}
