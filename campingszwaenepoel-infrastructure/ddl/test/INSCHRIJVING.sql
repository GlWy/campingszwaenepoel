CREATE TABLE `INSCHRIJVING` (
  `ID` int(11) NOT NULL auto_increment,
  `FK_STANDPLAATS_ID` int(11) NOT NULL,
  `DATE_VAN` date default NULL,
  `DATE_TOT` date default NULL,
  `SOORTHUURDER` varchar(9) default NULL,
  `OPMERKING` mediumtext,
  `FK_BADGE_ID` int(11) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB