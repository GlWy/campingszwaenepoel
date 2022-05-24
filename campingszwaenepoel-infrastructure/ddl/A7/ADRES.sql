CREATE TABLE `ADRES` (
  `ID` int(11) NOT NULL auto_increment,
  `STRAAT` varchar(255) NOT NULL,
  `HUISNUMMER` varchar(20) default NULL,
  `BUS` varchar(10) default NULL,
  `PLAATS` varchar(255) NOT NULL,
  `POSTCODE` varchar(6) NOT NULL,
  `LAND` varchar(100) default NULL,
  `FK_PERSOON_ID` int(10) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8