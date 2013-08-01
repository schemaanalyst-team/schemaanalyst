/**********************************************
 * Constraint coverage for NistDML181NotNulls *
 **********************************************/
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS LONG_NAMED_PEOPLE;
CREATE TABLE LONG_NAMED_PEOPLE (
	FIRSTNAME	VARCHAR(373)	NOT NULL,
	LASTNAME	VARCHAR(373)	NOT NULL,
	AGE	INT,
	PRIMARY KEY (FIRSTNAME, LASTNAME)
);
CREATE TABLE ORDERS (
	FIRSTNAME	VARCHAR(373)	NOT NULL,
	LASTNAME	VARCHAR(373)	NOT NULL,
	TITLE	VARCHAR(80),
	COST	NUMERIC(5, 2),
	FOREIGN KEY (FIRSTNAME, LASTNAME) REFERENCES LONG_NAMED_PEOPLE (FIRSTNAME, LASTNAME)
);
-- Coverage: 12/12 (100.00000%) 
-- Time to generate: 88ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 9ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('a', '', 0);
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('', '', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('', '', '', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('', '', '', 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FIRSTNAME, LASTNAME]" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(FIRSTNAME)" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 1ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES(NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(LASTNAME)" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('', NULL, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[FIRSTNAME, LASTNAME]" on table "ORDERS"
-- * Success: true
-- * Time: 24ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('b', '', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('`', '', '', 0);
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "NOT NULL(FIRSTNAME)" on table "ORDERS"
-- * Success: true
-- * Time: 22ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('`', '', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES(NULL, '', '', 0);
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(LASTNAME)" on table "ORDERS"
-- * Success: true
-- * Time: 32ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('aa', '', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('', NULL, '', 0);
-- * Number of objective function evaluations: 25
-- * Number of restarts: 0

