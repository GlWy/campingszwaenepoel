CREATE TABLE `STAMBOOM` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `NAAM` varchar(150) NOT NULL,
  `OPMERKING` varchar(255) default NULL,
  `FK_STANDPLAATS_ID` int(10) unsigned NOT NULL,
  `GENERATIE` tinyint(4) default NULL,
  `RANG` tinyint(4) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB