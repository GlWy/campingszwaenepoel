CREATE TABLE `ADMINISTRATIE` (
  `ID` int(11) NOT NULL auto_increment,
  `OPMERKING` mediumtext,
  `REGLEMENT_UITLEG_NAAM` int(1) default '0',
  `REGLEMENT_UITLEG_DATUM` date default NULL,
  `REGLEMENT_GETEKEND_NAAM` int(1) default '0',
  `REGLEMENT_GETEKEND_DATUM` date default NULL,
  `BAREEL_UITLEG_NAAM` int(1) default '0',
  `BAREEL_UITLEG_DATUM` date default NULL,
  `BAREEL_GETEKEND_NAAM` int(1) default '0',
  `BAREEL_GETEKEND_DATUM` date default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB