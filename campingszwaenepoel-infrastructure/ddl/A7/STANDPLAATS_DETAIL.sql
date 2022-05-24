CREATE TABLE `STANDPLAATS_DETAIL` (
  `ID` int(11) NOT NULL auto_increment,
  `NUMMER` tinyint(4) NOT NULL,
  `NAAM` varchar(100) default NULL,
  `DATUMVELD` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8