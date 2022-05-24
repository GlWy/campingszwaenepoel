
    create table `camping_zwaenepoel`.`GROND_DETAIL`(
        `ID` INT not null auto_increment,
       `OUD_NUMMER` VARCHAR(5),
       `AFMETING_PLAATS` VARCHAR(5),
       `WC_PUT` VARCHAR(15),
       `CAR_MAX` VARCHAR(5),
       `TROTTOIR` VARCHAR(9),
       `VOORTENT` VARCHAR(5),
       `GAS` DATE,
       `VERZEKERING` DATE,
       `TRAP` TINYINT,
       `BAK` VARCHAR(10),
       `ELEKTRICITEIT` VARCHAR(4),
       `RIOOL` VARCHAR(10),
       `AFVOER` VARCHAR(10),
        primary key (`ID`)
    );

    alter table `camping_zwaenepoel`.`GROND_DETAILS`  
        add index `PERSOON_ID`(`ID`), 
        add constraint `PERSOON_ID` 
        foreign key (`ID`) 
        references `camping_zwaenepoel`.`GROND_PERSOON`(`ID`) 
        on delete cascade ;
    alter table `camping_zwaenepoel`.`GROND_DETAILS`  
        add index `GROND_DETAILS_ID`(`ID`), 
        add constraint `GROND_DETAILS_ID` 
        foreign key (`ID`) 
        references `camping_zwaenepoel`.`GROND`(`ID`) 
        on delete cascade ;
    create unique index `PRIMARY` on `camping_zwaenepoel`.`GROND_DETAILS`(`ID`);
    create index `GROND_DETAILS_ID` on `camping_zwaenepoel`.`GROND_DETAILS`(`ID`);
    create index `PERSOON_ID` on `camping_zwaenepoel`.`GROND_DETAILS`(`ID`);