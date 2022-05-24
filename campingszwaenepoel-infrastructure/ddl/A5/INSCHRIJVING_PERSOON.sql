
    create table `CAMPING_ZWAENEPOEL`.`INSCHRIJVING_PERSOON`(
        `ID` INT not null auto_increment,
       `FK_INSCHRIJVING_ID` INT default '' not null,
       `FK_PERSOON_ID` INT default '' not null,
       `INSCHRIJVING_DATUM` DATE default '' not null,
        primary key (`ID`,`FK_INSCHRIJVING_ID`,`FK_PERSOON_ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`INSCHRIJVING_PERSOON`(`ID`,`FK_INSCHRIJVING_ID`,`FK_PERSOON_ID`);