CREATE TABLE `STANDPLAATS` (
  `ID` int(11) NOT NULL auto_increment,
  `PLAATSGROEP` char(1) NOT NULL default '',
  `PLAATSNUMMER` int(5) default NULL,
  `GESCHIEDENIS` mediumtext,
  `BADGE` varchar(10) default NULL,
  `ALGEMENE_OPMERKING` mediumtext,
  `FOTO_OPMERKING` mediumtext,
  `FK_ADMINISTRATIE_ID` int(11) default NULL,
  `FK_BADGE_ID` int(11) default NULL,
  `STANDPLAATS_OUD_ID` int(11) default NULL,
  `STANDPLAATS_OUD_BADGENUMMER` varchar(20) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM