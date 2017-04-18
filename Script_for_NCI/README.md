This code is developed as part of the TechLauncher project for exporting Research Graph records to Gephi. 

## How to Run the Shell Script:

1. Download and install [Neo4j](https://neo4j.com/download/);

2. cd into Neo4j folder and replace the folder data with NCI data;

3. Copy "main.sh" and "export-NCI-gephi.cli" files under Neo4j folder;

4. Open the "main.sh" file and replace Neo4j folder path with "#neo4j folder path#";

5. Go to the terminal and run

```
sh main.sh
```
You will receive the following message:
```
replace lib/import-tools-2.3.2.jar? [y]es, [n]o, [A]ll, [N]one, [r]ename:
```
```
replace lib/opencsv-2.3.jar? [y]es, [n]o, [A]ll, [N]one, [r]ename: 
```
```
replace lib/geoff-0.5.0.jar? [y]es, [n]o, [A]ll, [N]one, [r]ename:
```
```
replace lib/mapdb-0.9.3.jar? [y]es, [n]o, [A]ll, [N]one, [r]ename:
```
Please press 'y'
