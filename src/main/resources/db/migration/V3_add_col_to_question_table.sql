create table QUESTION
(
    ID            INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_3027826C_31C8_4389_8D14_A33B6892E426) auto_increment,
    TITLE         VARCHAR(50),
    DESCRIPTION   CLOB,
    GMT_CREATE    BIGINT,
    GMT_MODIFIED  BIGINT,
    CREATOR       INTEGER,
    COMMENT_COUNT INTEGER default 0,
    VIEW_COUNT    INTEGER default 0,
    LIKE_COUNT    INTEGER default 0,
    TAG           VARCHAR(256),
    constraint QUESTION_PK
        primary key (ID)
);