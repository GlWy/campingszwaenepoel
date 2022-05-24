
    create table `CAMPING_ZWAENEPOEL`.`STAMBOOM`(
        `ID` INT UNSIGNED not null auto_increment,
       `NAAM` VARCHAR(150) default '' not null,
       `OPMERKING` VARCHAR(255),
       `FK_STANDPLAATS_ID` INT UNSIGNED default '' not null,
       `GENERATIE` TINYINT,
       `RANG` TINYINT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`STAMBOOM`(`ID`);