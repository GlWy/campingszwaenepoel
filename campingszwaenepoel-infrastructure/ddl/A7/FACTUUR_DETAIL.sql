CREATE TABLE `FACTUUR_DETAIL` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `FK_STANDPLAATS_ID` int(10) unsigned default NULL,
  `DATUM` date default NULL,
  `AARD_BETALING` varchar(12) NOT NULL,
  `BEDRAG` double default NULL,
  `UITSTELKOSTEN` double default NULL,
  `RAPPEL` double default NULL,
  `ANDEREKOSTEN` double default NULL,
  `OPMERKING` varchar(255) default NULL,
  `DATUM_AANMAAK` bigint(20) unsigned default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8