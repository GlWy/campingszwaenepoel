CREATE TABLE `INSCHRIJVING_PERSOON` (
  `ID` int(11) NOT NULL auto_increment,
  `FK_INSCHRIJVING_ID` int(11) NOT NULL,
  `FK_PERSOON_ID` int(11) NOT NULL,
  `INSCHRIJVING_DATUM` bigint(20) unsigned default NULL,
  `GEZINSPOSITIE` varchar(10) NOT NULL,
  `HUURDERSPOSITIE` varchar(13) NOT NULL,
  PRIMARY KEY  (`ID`,`FK_INSCHRIJVING_ID`,`FK_PERSOON_ID`)
) ENGINE=InnoDB