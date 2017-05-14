echo "Installation"
curl http://dist.neo4j.org/jexp/shell/neo4j-shell-tools_2.3.2.zip -o neo4j-shell-tools.zip
unzip neo4j-shell-tools.zip -d lib

echo "Enable Neo4j shell-tool"
./bin/neo4j restart
./bin/neo4j-shell -c -file  ./export-NCI-gephi.cli

sleep 5s

./bin/neo4j stop