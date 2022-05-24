
    create table `CAMPING_ZWAENEPOEL`.`ADMINISTRATIE`(
        `ID` INT not null auto_increment,
       `OPMERKING` MEDIUMTEXT,
       `REGLEMENT_UITLEG_NAAM` INT default '0',
       `REGLEMENT_UITLEG_DATUM` DATE,
       `REGLEMENT_GETEKEND_NAAM` INT default '0',
       `REGLEMENT_GETEKEND_DATUM` DATE,
       `BAREEL_UITLEG_NAAM` INT default '0',
       `BAREEL_UITLEG_DATUM` DATE,
       `BAREEL_GETEKEND_NAAM` INT default '0',
       `BAREEL_GETEKEND_DATUM` DATE,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`ADMINISTRATIE`(`ID`);