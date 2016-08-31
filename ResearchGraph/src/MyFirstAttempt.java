//import org.neo4j.cypher.ExecutionEngine;
//import org.neo4j.cypher.ExecutionResult;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.helpers.collection.Visitor;
//import org.neo4j.kernel.impl.util.StringLogger;
//import org.neo4j.kernel.logging.LogMarker;
//
///**
// * Created by wangkun on 25/08/2016.
// */
//public class MyFirstAttempt {
//    public static void main(String[] args) {
//
//        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
//
//        //GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase("/Users/wangkun/Desktop/test/graph.db");
//
//        //File storeDir = new File("/Users/wangkun/Desktop/ResearchGraph/neo4j-community-2.1.3/data/");
//
//        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase("/Users/wangkun/Desktop/ResearchGraph/neo4j-community-2.1.3/data/graph.db");
//
//
//        ExecutionEngine execEngine = new ExecutionEngine(graphDb, new StringLogger() {
//            @Override
//            public void logLongMessage(String s, Visitor<LineLogger, RuntimeException> visitor, boolean b) {
//
//            }
//
//            @Override
//            public void logMessage(String s, boolean b) {
//
//            }
//
//            @Override
//            public void logMessage(String s, LogMarker logMarker) {
//
//            }
//
//            @Override
//            public void logMessage(String s, Throwable throwable, boolean b) {
//
//            }
//
//            @Override
//            public void addRotationListener(Runnable runnable) {
//
//            }
//
//            @Override
//            public void flush() {
//
//            }
//
//            @Override
//            public void close() {
//
//            }
//
//            @Override
//            protected void logLine(String s) {
//
//            }
//        });
//
//
//        ExecutionResult execResult = execEngine.execute("MATCH (n:`Person`) RETURN n LIMIT 25");
//        String results = execResult.dumpToString();
//        System.out.println(results);
//    }
//}
