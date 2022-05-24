
    create table `CAMPING_ZWAENEPOEL`.`WAGEN`(
        `ID` INT not null auto_increment,
       `FK_PERSOON_ID` INT default '' not null,
       `NUMMERPLAAT` VARCHAR(8) default '' not null,
       `MERK` VARCHAR(256),
       `OPMERKING` VARCHAR(255),
       `STICKER` VARCHAR(50),
       `FK_NUMMERPLAAT_ID` INT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`WAGEN`(`ID`);
    create index `FK_PERSOON_ID` on `CAMPING_ZWAENEPOEL`.`WAGEN`(`FK_PERSOON_ID`);