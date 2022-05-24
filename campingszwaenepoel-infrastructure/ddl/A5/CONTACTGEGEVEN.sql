
    create table `CAMPING_ZWAENEPOEL`.`CONTACTGEGEVEN`(
        `ID` INT not null auto_increment,
       `FK_PERSOON_ID` INT default '' not null,
       `CONTACTGEGEVEN_TYPE` VARCHAR(32) default '' not null,
       `WAARDE` VARCHAR(256) default '' not null,
       `NUMMER` VARCHAR(20),
       `ZONE` VARCHAR(20),
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`CONTACTGEGEVEN`(`ID`);