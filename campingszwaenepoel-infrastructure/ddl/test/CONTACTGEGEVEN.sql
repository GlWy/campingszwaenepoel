CREATE TABLE `CONTACTGEGEVEN` (
  `ID` int(11) NOT NULL auto_increment,
  `FK_PERSOON_ID` int(11) NOT NULL,
  `CONTACTGEGEVEN_TYPE` varchar(32) NOT NULL default '',
  `WAARDE` varchar(256) NOT NULL default '',
  `NUMMER` varchar(20) default NULL,
  `ZONE` varchar(20) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB