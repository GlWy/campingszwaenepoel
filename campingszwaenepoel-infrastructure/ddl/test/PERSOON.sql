CREATE TABLE `PERSOON` (
  `ID` int(11) NOT NULL auto_increment,
  `NAAM` varchar(100) NOT NULL,
  `VOORNAAM` varchar(100) NOT NULL,
  `GEBOORTEPLAATS` varchar(255) default NULL,
  `GEBOORTEDATUM` date default NULL,
  `PARTNER_ID` int(11) default '0',
  `TAAL` varchar(3) NOT NULL default 'NL',
  `OPMERKING` varchar(255) default NULL,
  `GESLACHT` varchar(5) NOT NULL,
  `FK_ADRES_ID` int(11) default NULL,
  `BADGE` varchar(10) default NULL,
  `STRAAT` varchar(255) default NULL,
  `HUISNUMMER` varchar(20) default NULL,
  `PLAATS` varchar(255) default NULL,
  `FK_OUD_PERSOON_ID` int(10) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB