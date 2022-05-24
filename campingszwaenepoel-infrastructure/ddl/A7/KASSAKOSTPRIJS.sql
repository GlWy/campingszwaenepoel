CREATE TABLE `KASSA_KOSTPRIJS` (
  `ID` int(11) NOT NULL auto_increment,
  `NUMMER` int(11) NOT NULL,
  `NAAM` varchar(60) default NULL,
  `WAARDE` double default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (1, "Auto 100", 3);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (2, "Auto 150", 4);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (3, "Waarborg", 60);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (4, "Jeton", 1.25);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (5, "Zak Geel", 1);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (6, "Zak Blauw", 0.5);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (7, "Car", 6.5);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (8, "Tent", 5);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (9, "Volwassenen", 2.5);
INSERT INTO KASSA_KOSTPRIJS (NUMMER, NAAM, WAARDE) VALUES (10, "Kinderen", 2);