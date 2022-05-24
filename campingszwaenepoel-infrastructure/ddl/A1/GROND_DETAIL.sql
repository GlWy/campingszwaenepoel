
    create table `CAMPING_ZWAENEPOEL`.`GROND_DETAIL`(
        `ID` INT not null auto_increment,
       `NUMMER` TINYINT default '' not null,
       `NAAM` VARCHAR(100) default '' not null,
       `DATUMVELD` CHAR(1) default 'O' not null,
       `DATUM_VERSTREKEN` BIT default '0' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`GROND_DETAIL`(`ID`);