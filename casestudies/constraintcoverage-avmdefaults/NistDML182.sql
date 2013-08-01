/**************************************
 * Constraint coverage for NistDML182 *
 **************************************/
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS ID_CODES;
CREATE TABLE ID_CODES (
	CODE1	INT,
	CODE2	INT,
	CODE3	INT,
	CODE4	INT,
	CODE5	INT,
	CODE6	INT,
	CODE7	INT,
	CODE8	INT,
	CODE9	INT,
	CODE10	INT,
	CODE11	INT,
	CODE12	INT,
	CODE13	INT,
	CODE14	INT,
	CODE15	INT,
	PRIMARY KEY (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15)
);
CREATE TABLE ORDERS (
	CODE1	INT,
	CODE2	INT,
	CODE3	INT,
	CODE4	INT,
	CODE5	INT,
	CODE6	INT,
	CODE7	INT,
	CODE8	INT,
	CODE9	INT,
	CODE10	INT,
	CODE11	INT,
	CODE12	INT,
	CODE13	INT,
	CODE14	INT,
	CODE15	INT,
	TITLE	VARCHAR(80),
	COST	NUMERIC(5, 2),
	FOREIGN KEY (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) REFERENCES ID_CODES (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 172ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 23ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15]" on table "ID_CODES"
-- * Success: true
-- * Time: 2ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15]" on table "ORDERS"
-- * Success: true
-- * Time: 147ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 53
-- * Number of restarts: 0

