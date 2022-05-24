
    create table `CAMPING_ZWAENEPOEL`.`ADRES`(
        `ID` INT not null auto_increment,
       `STRAAT` VARCHAR(255) default '' not null,
       `HUISNUMMER` VARCHAR(20),
       `BUS` VARCHAR(10),
       `PLAATS` VARCHAR(255) default '' not null,
       `POSTCODE` VARCHAR(6) default '' not null,
       `LAND` VARCHAR(100),
       `FK_PERSOON_ID` INT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`ADRES`(`ID`);