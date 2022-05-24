
    create table `CAMPING_ZWAENEPOEL`.`GROND_INFORMATIE`(
        `ID` INT not null auto_increment,
       `FK_GROND_ID` INT default '' not null,
       `NUMMER` TINYINT default '' not null,
       `WAARDE` VARCHAR(100),
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`GROND_INFORMATIE`(`ID`);