
    create table `CAMPING_ZWAENEPOEL`.`STANDPLAATS`(
        `ID` INT not null auto_increment,
       `PLAATSGROEP` CHAR(1) default '' not null,
       `PLAATSNUMMER` INT,
       `GESCHIEDENIS` MEDIUMTEXT,
       `BADGE` VARCHAR(10),
       `ALGEMENE_OPMERKING` MEDIUMTEXT,
       `FOTO_OPMERKING` MEDIUMTEXT,
       `FK_ADMINISTRATIE_ID` INT,
       `FK_BADGE_ID` INT,
       `STANDPLAATS_OUD_ID` INT,
       `STANDPLAATS_OUD_BADGENUMMER` VARCHAR(20),
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`STANDPLAATS`(`ID`);