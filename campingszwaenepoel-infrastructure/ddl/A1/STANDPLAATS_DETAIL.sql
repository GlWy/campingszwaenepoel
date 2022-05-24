
    create table `CAMPING_ZWAENEPOEL`.`STANDPLAATS_DETAIL`(
        `ID` INT not null auto_increment,
       `NUMMER` TINYINT default '' not null,
       `NAAM` VARCHAR(100),
       `DATUMVELD` BIT default '0' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`STANDPLAATS_DETAIL`(`ID`);