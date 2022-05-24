
    create table `CAMPING_ZWAENEPOEL`.`FACTUUR`(
        `ID` INT UNSIGNED not null auto_increment,
       `JAAR` INT UNSIGNED default '' not null,
       `BETAALD` BIT default '0' not null,
       `FK_STANDPLAATS_ID` INT UNSIGNED default '' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`FACTUUR`(`ID`);
    create index `FK_STANDPLAATS_ID` on `CAMPING_ZWAENEPOEL`.`FACTUUR`(`FK_STANDPLAATS_ID`);