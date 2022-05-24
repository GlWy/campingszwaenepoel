
    create table `CAMPING_ZWAENEPOEL`.`STANDPLAATS_BETALER`(
        `ID` INT not null auto_increment,
       `FK_HOOFDBETALER_ID` INT,
       `FK_BETALER_ID` INT,
       `DATUM_VAN` DATE default '' not null,
       `DATUM_TOT` DATE,
       `OPMERKING` MEDIUMTEXT,
       `FK_ADMINISTRATIE_ID` INT,
       `ADMINISTRATIE_GEDAAN` BIT default '0' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`STANDPLAATS_BETALER`(`ID`);