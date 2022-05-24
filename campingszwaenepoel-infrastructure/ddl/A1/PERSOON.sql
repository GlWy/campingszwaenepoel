
    create table `CAMPING_ZWAENEPOEL`.`PERSOON`(
        `ID` INT not null auto_increment,
       `NAAM` VARCHAR(100) default '' not null,
       `VOORNAAM` VARCHAR(100) default '' not null,
       `VAST_LOS` VARCHAR(4) default '' not null,
       `FUNCTIE` VARCHAR(10),
       `POSITIE` VARCHAR(13) default '' not null,
       `BETALER` BIT default '0' not null,
       `PARTNER_ID` INT,
       `TAAL` VARCHAR(3) default 'NL' not null,
       `FK_ADMINISTRATIE_ID` INT default '' not null,
       `OPMERKING` VARCHAR(255),
       `OPMERKING_SPECIAAL` VARCHAR(255),
       `GESLACHT` VARCHAR(5) default '' not null,
       `NUMMERPLAAT` VARCHAR(10),
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`PERSOON`(`ID`);