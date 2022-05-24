CREATE TABLE `FACTUUR_DETAIL` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `FK_FACTUUR_ID` int(10) unsigned NOT NULL,
  `DATUM` varchar(15) default NULL,
  `AARD_BETALING` varchar(12) NOT NULL,
  `BEDRAG` double default NULL,
  `UITSTELKOSTEN` double default NULL,
  `RAPPEL` double default NULL,
  `ANDEREKOSTEN` double default NULL,
  `OPMERKING` varchar(255) default NULL,
  `DATUM_AANMAAK` bigint(20) unsigned default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB