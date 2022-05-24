
    create table `CAMPING_ZWAENEPOEL`.`CONFIGURATIE`(
        `ID` INT not null auto_increment,
       `NAAM` VARCHAR(255) default '' not null,
       `WAARDE` VARCHAR(255) default '' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`CONFIGURATIE`(`ID`);