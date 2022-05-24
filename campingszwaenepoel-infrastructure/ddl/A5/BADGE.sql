
    create table `CAMPING_ZWAENEPOEL`.`BADGE`(
        `ID` INT not null auto_increment,
       `BADGENUMMER` VARCHAR(20) unique,
       `BADGETYPE` VARCHAR(4) default '' not null,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `CAMPING_ZWAENEPOEL`.`BADGE`(`ID`);
    create unique index `BADGENUMBER` on `CAMPING_ZWAENEPOEL`.`BADGE`(`BADGENUMMER`);