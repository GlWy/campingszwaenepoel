
    create table `CAMPING_ZWAENEPOEL`.`STANDPLAATS`(
        `ID` INT not null auto_increment,
       `PLAATSGROEP` CHAR(1) default '' not null,
       `PLAATSNUMMER` INT,
       `GESCHIEDENIS` MEDIUMTEXT,
       `BADGE` VARCHAR(10),
       `ALGEMENE_OPMERKING` MEDIUMTEXT,
       `BETALER_OPMERKING` MEDIUMTEXT,
       `FOTO_OPMERKING` MEDIUMTEXT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`STANDPLAATS`(`ID`);