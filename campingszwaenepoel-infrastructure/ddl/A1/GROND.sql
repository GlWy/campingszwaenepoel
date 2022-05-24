
    create table `CAMPING_ZWAENEPOEL`.`STANDPLAATS`(
        `ID` INT not null auto_increment,
       `PLAATSGROEP` CHAR default '' not null,
       `PLAATSNUMMER` TINYINT not null,
       `CREATIE_DATUM` DATE not null,
       `GESCHIEDENIS` MEDIUMTEXT,
       `BADGE` VARCHAR(10),
       `ALGEMENE_OPMERKING` MEDIUMTEXT,
       `BETALER_OPMERKING` MEDIUMTEXT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`STANDPLAATS`(`ID`);