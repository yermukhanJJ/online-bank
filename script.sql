CREATE TABLE ROLE_TAB (
    ID serial,
    TITLE varchar(255) unique NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE PROFILE_TAB (
    ID serial,
    FIRST_NAME varchar(255) NOT NULL,
    LAST_NAME varchar(255) NOT NULL,
    USERNAME varchar(255) unique NOT NULL,
    PASSWORD varchar,
    EMAIL varchar(255) unique NOT NULL,
    EMAIL_CONFIRMED bool NOT NULL,
    BLOCKED bool NOT NULL,
    CREATE_AT timestamp with time zone NOT NULL,
    UPDATE_AT timestamp with time zone,
    PRIMARY KEY (ID)
);

CREATE TABLE ROLE_PROFILE (
    PROFILE_ID bigint,
    ROLE_ID int,
    FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE_TAB(ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE_TAB(ID)
);

INSERT INTO ROLE_TAB VALUES (1, 'USER');
INSERT INTO ROLE_TAB VALUES (2, 'ADMIN');

CREATE TABLE CONFIRMATION_TOKEN (
    ID serial,
    TOKEN varchar unique NOT NULL,
    CREATED_AT timestamp with time zone,
    EXPIRES_AT timestamp with time zone,
    CONFIRMED_AT timestamp with time zone,
    PROFILE_ID bigint NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE_TAB(ID)
);

CREATE TABLE BANK_ACCOUNT_TAB (
    ID serial,
    ACCOUNT_NUMBER varchar unique NOT NULL,
    BALANCE float default 0.0,
    PROFILE_ID bigint NOT NULL,
    CREATE_AT timestamp with time zone NOT NULL,
    UPDATE_AT timestamp with time zone DEFAULT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE_TAB(ID)
);