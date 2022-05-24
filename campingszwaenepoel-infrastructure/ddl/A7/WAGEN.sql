CREATE TABLE `WAGEN` (
  `ID` int(11) NOT NULL auto_increment,
  `FK_PERSOON_ID` int(11) NOT NULL,
  `NUMMERPLAAT` varchar(8) NOT NULL,
  `MERK` varchar(256) default NULL,
  `OPMERKING` varchar(255) default NULL,
  `STICKER` varchar(50) default NULL,
  `FK_NUMMERPLAAT_ID` int(10) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_PERSOON_ID` (`FK_PERSOON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf