SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `FACTUUR_DETAIL` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `FK_FACTUUR_ID` int(10) unsigned NOT NULL,
  `DATUM` date NOT NULL,
  `AARD_BETALING` varchar(12) NOT NULL,
  `BEDRAG` int(11) DEFAULT NULL,
  `UITSTELKOSTEN` int(11) DEFAULT NULL,
  `RAPPEL` int(11) DEFAULT NULL,
  `ANDEREKOSTEN` int(11) DEFAULT NULL,
  `OPMERKING` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FACTUUR_ID` (`FK_FACTUUR_ID`),
  CONSTRAINT `FK_FACTUUR_ID` FOREIGN KEY (`FK_FACTUUR_ID`) REFERENCES `factuur` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `FACTUUR_DETAIL` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `FK_FACTUUR_ID` int(10) unsigned NOT NULL,
  `DATUM` date NOT NULL,
  `AARD_BETALING` varchar(12) NOT NULL,
  `BEDRAG` int(11) DEFAULT NULL,
  `UITSTELKOSTEN` int(11) DEFAULT NULL,
  `RAPPEL` int(11) DEFAULT NULL,
  `ANDEREKOSTEN` int(11) DEFAULT NULL,
  `OPMERKING` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FACTUUR_ID` (`FK_FACTUUR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
