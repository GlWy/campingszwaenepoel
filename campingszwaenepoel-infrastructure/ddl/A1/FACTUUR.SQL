SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `CAMPING_ZWAENEPOEL`.`FACTUUR` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `JAAR` int(10) unsigned NOT NULL,
  `BETAALD` BOOLEAN NOT NULL DEFAULT 0,
  `FK_STANDPLAATS_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STANDPLAATS_ID` (`FK_STANDPLAATS_ID`),
  CONSTRAINT `FK_STANDPLAATS_ID` FOREIGN KEY (`FK_STANDPLAATS_ID`) REFERENCES `standplaats` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `CAMPING_ZWAENEPOEL`.`FACTUUR` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `JAAR` int(10) unsigned NOT NULL,
  `BETAALD` BOOLEAN NOT NULL DEFAULT 0,
  `FK_STANDPLAATS_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STANDPLAATS_ID` (`FK_STANDPLAATS_ID`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  