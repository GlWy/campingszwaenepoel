
    create table `CAMPING_ZWAENEPOEL`.`ADRES`(
        `ID` INT not null auto_increment,
       `STRAAT` VARCHAR(255) default '' not null,
       `HUISNUMMER` VARCHAR(10) default '' not null,
       `BUS` VARCHAR(4) default '' not null,
       `PLAATS` VARCHAR(255) default '' not null,
       `POSTCODE` VARCHAR(6) default '' not null,
       `LAND` VARCHAR(100) default '' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`ADRES`(`ID`);