
    create table `CAMPING_ZWAENEPOEL`.`INSCHRIJVING`(
        `ID` INT not null auto_increment,
       `FK_STANDPLAATS_ID` INT default '' not null,
       `DATE_VAN` DATE,
       `DATE_TOT` DATE,
       `SOORTHUURDER` VARCHAR(9),
       `OPMERKING` MEDIUMTEXT,
       `FK_HOOFDBETALER_ID` INT,
       `FK_BETALER_ID` INT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`INSCHRIJVING`(`ID`);