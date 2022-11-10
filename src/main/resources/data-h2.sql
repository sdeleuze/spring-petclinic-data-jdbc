INSERT INTO VET VALUES (default, 'James', 'Carter');
INSERT INTO VET VALUES (default, 'Helen', 'Leary');
INSERT INTO VET VALUES (default, 'Linda', 'Douglas');
INSERT INTO VET VALUES (default, 'Rafael', 'Ortega');
INSERT INTO VET VALUES (default, 'Henry', 'Stevens');
INSERT INTO VET VALUES (default, 'Sharon', 'Jenkins');

INSERT INTO SPECIALTY VALUES (1, 'radiology');
INSERT INTO SPECIALTY VALUES (2, 'surgery');
INSERT INTO SPECIALTY VALUES (3, 'dentistry');

INSERT INTO VET_SPECIALTY VALUES (2, 1);
INSERT INTO VET_SPECIALTY VALUES (3, 2);
INSERT INTO VET_SPECIALTY VALUES (3, 3);
INSERT INTO VET_SPECIALTY VALUES (4, 2);
INSERT INTO VET_SPECIALTY VALUES (5, 1);

INSERT INTO PET_TYPE VALUES (default, 'cat');
INSERT INTO PET_TYPE VALUES (default, 'dog');
INSERT INTO PET_TYPE VALUES (default, 'lizard');
INSERT INTO PET_TYPE VALUES (default, 'snake');
INSERT INTO PET_TYPE VALUES (default, 'bird');
INSERT INTO PET_TYPE VALUES (default, 'hamster');

INSERT INTO OWNER VALUES (default, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO OWNER VALUES (default, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO OWNER VALUES (default, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO OWNER VALUES (default, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO OWNER VALUES (default, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO OWNER VALUES (default, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO OWNER VALUES (default, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO OWNER VALUES (default, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO OWNER VALUES (default, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO OWNER VALUES (default, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO PET VALUES (default, 'Leo', '2010-09-07', 1, 1);
INSERT INTO PET VALUES (default, 'Basil', '2012-08-06', 6, 2);
INSERT INTO PET VALUES (default, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO PET VALUES (default, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO PET VALUES (default, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO PET VALUES (default, 'George', '2010-01-20', 4, 5);
INSERT INTO PET VALUES (default, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO PET VALUES (default, 'Max', '2012-09-04', 1, 6);
INSERT INTO PET VALUES (default, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO PET VALUES (default, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO PET VALUES (default, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO PET VALUES (default, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO PET VALUES (default, 'Sly', '2012-06-08', 1, 10);

INSERT INTO VISIT VALUES (default, 7, '2013-01-01', 'rabies shot');
INSERT INTO VISIT VALUES (default, 8, '2013-01-02', 'rabies shot');
INSERT INTO VISIT VALUES (default, 8, '2013-01-03', 'neutered');
INSERT INTO VISIT VALUES (default, 7, '2013-01-04', 'spayed');
