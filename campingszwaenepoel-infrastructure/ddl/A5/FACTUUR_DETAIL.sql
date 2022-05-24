
    create table `CAMPING_ZWAENEPOEL`.`FACTUUR_DETAIL`(
        `ID` INT UNSIGNED not null auto_increment,
       `FK_FACTUUR_ID` INT UNSIGNED default '' not null,
       `DATUM` VARCHAR(15),
       `AARD_BETALING` VARCHAR(12) default '' not null,
       `BEDRAG` INT,
       `UITSTELKOSTEN` INT,
       `RAPPEL` INT,
       `ANDEREKOSTEN` INT,
       `OPMERKING` VARCHAR(255),
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`FACTUUR_DETAIL`(`ID`);
    create index `FK_FACTUUR_ID` on `CAMPING_ZWAENEPOEL`.`FACTUUR_DETAIL`(`FK_FACTUUR_ID`);