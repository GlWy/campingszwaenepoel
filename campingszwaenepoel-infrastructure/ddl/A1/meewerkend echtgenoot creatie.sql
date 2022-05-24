create table MEEWERKEND_ECHTGENOOT
(
  ID			 VARCHAR2(32) not null,
  NAAM			 VARCHAR2(256) not null,
  VOORNAAM       VARCHAR2(256) not null,
  INSZ		     VARCHAR2(11) not null,
  TAAL           VARCHAR2(2),
  GEBOORTEPLAATS VARCHAR2(256),
  GEBOORTELAND   VARCHAR2(3),
  GEBOORTEDATUM  TIMESTAMP,
  GESLACHT       VARCHAR2(1)
)
alter table MEEWERKEND_ECHTGENOOT add constraint PK_ID primary key (ID);
