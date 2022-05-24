
    create table `CAMPING_ZWAENEPOEL`.`GROND_PERSOON`(
        `ID` INT not null auto_increment,
       `FK_GROND_ID` INT default '' not null,
       `FK_PERSOON_ID` INT default '' not null,
        primary key (`ID`,`FK_GROND_ID`,`FK_PERSOON_ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`GROND_PERSOON`(`ID`,`FK_GROND_ID`,`FK_PERSOON_ID`);