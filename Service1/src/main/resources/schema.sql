DROP TABLE IF EXISTS testdb;

CREATE TABLE ENTITY1 (
                         id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                         name VARCHAR(50),
                         status VARCHAR(20) DEFAULT 'PENDING'
);
