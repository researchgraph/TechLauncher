// This script imports CSV into Neo4j DB
// Hao Zhang

export FILEPATH=file:///Users/PATH_TO_CSV/example.csv

// example: importing institute from grid
// institute: grid_id, name, wikipedia_url, email_address, established

// Uniqueness constraints.
CREATE CONSTRAINT ON (i:institute) ASSERT i.grid_id IS UNIQUE;

// Load.
USING PERIODIC COMMIT
LOAD CSV WITH HEADERS 
FROM {FILEPATH} AS line
WITH DISTINCT line

CREATE (institute:institute { grid_id: line.grid_id, name: line.name, wikipedia: line.`wikipedia_url`, email: line.`email_address`, established: TOINT(line.`established`) })
;
