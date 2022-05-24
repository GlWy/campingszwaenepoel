
    create table `CAMPING_ZWAENEPOEL`.`PERSOON`(
        `ID` INT not null auto_increment,
       `NAAM` VARCHAR(100) default '' not null,
       `VOORNAAM` VARCHAR(100) default '' not null,
       `GEBOORTEPLAATS` VARCHAR(255),
       `GEBOORTEDATUM` DATE,
       `GEZINSPOSITIE` VARCHAR(10),
       `HUURDERSPOSITIE` VARCHAR(13),
       `PARTNER_ID` INT default '0',
       `TAAL` VARCHAR(3) default 'NL' not null,
       `OPMERKING` VARCHAR(255),
       `GESLACHT` VARCHAR(5) default '' not null,
       `FK_ADRES_ID` INT,
       `BADGE` VARCHAR(10),
       `STRAAT` VARCHAR(255),
       `HUISNUMMER` VARCHAR(20),
       `PLAATS` VARCHAR(255),
       `FK_OUD_PERSOON_ID` INT,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`PERSOON`(`ID`);