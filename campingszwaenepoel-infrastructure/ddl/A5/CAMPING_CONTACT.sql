
    create table `CAMPING_ZWAENEPOEL`.`CAMPING_CONTACT`(
        `ID` INT UNSIGNED not null auto_increment,
       `NAAM` VARCHAR(255),
       `TELEFOON` VARCHAR(255),
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`CAMPING_CONTACT`(`ID`);