CREATE TABLE `FACTUUR` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `JAAR` int(10) unsigned NOT NULL,
  `BETAALD` tinyint(1) NOT NULL default '0',
  `FK_STANDPLAATS_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_STANDPLAATS_ID` (`FK_STANDPLAATS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8