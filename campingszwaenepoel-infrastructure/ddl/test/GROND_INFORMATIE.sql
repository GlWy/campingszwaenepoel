CREATE TABLE `GROND_INFORMATIE` (
  `ID` int(11) NOT NULL auto_increment,
  `FK_GROND_ID` int(11) NOT NULL,
  `NUMMER` tinyint(4) NOT NULL,
  `WAARDE` varchar(100) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB