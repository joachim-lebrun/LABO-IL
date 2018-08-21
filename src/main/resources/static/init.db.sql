DROP DATABASE LABOIL;
CREATE DATABASE LABOIL;

USE LABOIL;

CREATE TABLE USER (
  USER_ID      CHAR(36) PRIMARY KEY,
  FIRST_NAME   VARCHAR(30)  NOT NULL,
  LAST_NAME    VARCHAR(30)  NOT NULL,
  ADDRESS      VARCHAR(100) NOT NULL,
  PHONE_NUMBER INT(20),
  BIRTH_DATE   VARCHAR(100) NOT NULL,
  EMAIL        VARCHAR(255) NOT NULL,
  PASSWORD     VARCHAR(100) NOT NULL,
  REG_NAT      VARCHAR(100) NOT NULL,
  ENABLED      BOOLEAN      not null default true
);

CREATE TABLE CITIZEN (
  CITIZEN_ID CHAR(36) PRIMARY KEY,
  USER_ID    CHAR(36)    NOT NULL,
  TOWN       VARCHAR(36) NOT NULL
);

CREATE TABLE EMPLOYEE (
  EMPLOYEE_ID CHAR(36) PRIMARY KEY,
  USER_ID     CHAR(36) NOT NULL,
  SERVICE_ID  CHAR(36) NOT NULL
);

create table ROLE (
  EMAIL  varchar(50) not null,
  RIGHTS varchar(50) not null
);

CREATE TABLE SERVICE (
  SERVICE_ID    CHAR(36) PRIMARY KEY,
  NAME          VARCHAR(30)  NOT NULL,
  TOWN_ID       CHAR(36)     NOT NULL,
  ADMINISTRATOR CHAR(36)     NOT NULL,
  ADDRESS       VARCHAR(255) NOT NULL
);

CREATE TABLE TOWN (
  TOWN_ID       char(36) PRIMARY KEY,
  MAYOR_ID      char(36)     NOT NULL,
  NAME          VARCHAR(30)  NOT NULL,
  TOWN_LANGUAGE VARCHAR(30)  NOT NULL,
  POSTAL_CODE   INT(8)       NOT NULL,
  COUNTRY       VARCHAR(30)  NOT NULL,
  ADDRESS       VARCHAR(30)  NOT NULL,
  EMAIL         VARCHAR(255) NOT NULL,
  PHONE_NUMBER  VARCHAR(30)  NOT NULL,
  LOGO_PATH     VARCHAR(255) NOT NULL
);

CREATE TABLE OFFICIAL_DOC (
  DOCUMENT_ID   CHAR(36) PRIMARY KEY,
  CREATION_DATE CHAR(10)     NOT NULL,
  PATH          VARCHAR(255) NOT NULL,
  DEMAND_ID     CHAR(36)     NOT NULL
);

CREATE TABLE DEMAND (
  DEMAND_ID   CHAR(36) PRIMARY KEY,
  NAME        VARCHAR(255) NOT NULL,
  COMMUNAL_NAME        VARCHAR(255),
  SERVICE_ID  CHAR(36)     NOT NULL,
  CREATOR     CHAR(36)     NOT NULL,
  VERIFICATOR CHAR(36)
);

CREATE TABLE EVENT (
  EVENT_ID      CHAR(36) PRIMARY KEY,
  USER_ID       CHAR(36)     NOT NULL,
  COMMENT       VARCHAR(255),
  DEMAND_ID     CHAR(36)     NOT NULL,
  STATUS        VARCHAR(20)  NOT NULL,
  CREATION_DATE VARCHAR(10)         NOT NULL
);

CREATE TABLE DEMAND_INFO (
  DEMAND_INFO_ID CHAR(36) PRIMARY KEY,
  DEMAND_ID      CHAR(36)     NOT NULL,
  INFO_KEY       VARCHAR(255) NOT NULL,
  INFO_VALUE     VARCHAR(255) NOT NULL
);

CREATE TABLE LINK_DOCUMENT (
  LINK_DOCUMENT_ID CHAR(36) PRIMARY KEY,
  DEMAND_ID        CHAR(36)     NOT NULL,
  PATH             VARCHAR(255) NOT NULL,
  NAME             VARCHAR(255) NOT NULL
);

INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUMBER, BIRTH_DATE, EMAIL, PASSWORD, REG_NAT)
VALUES ('a1d71f3b-1234-4343-9194-badd9a882784',
        'Jannette',
        'Jackson',
        '321 rue de la pleine lune',
        13456789,
        '8rKfwJM5PvYztyZfUcWfqQ',
        'themoonwalkette@gmail.com',
        '$2a$10$qNKn877j75G9h0Y3qHoHdO8YnQyDPv8PrHT3obNTircFFjpBN83Ru',
        '');
INSERT INTO EMPLOYEE (EMPLOYEE_ID, SERVICE_ID, USER_ID)
VALUES ('a1d72fed-1234-4343-9194-badd9a882784', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a1d71f3b-1234-4343-9194-badd9a882784');
INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUMBER, BIRTH_DATE, EMAIL, PASSWORD, REG_NAT)
VALUES ('a1e71f3b-1234-4343-9194-badd9a882784',
        'Michael',
        'Jackson',
        '321 rue de la pleine lune',
        13456789,
        '8rKfwJM5PvYztyZfUcWfqQ',
        'themoonwalker@gmail.com',
        '$2a$10$qNKn877j75G9h0Y3qHoHdO8YnQyDPv8PrHT3obNTircFFjpBN83Ru',
        '');
INSERT INTO EMPLOYEE (EMPLOYEE_ID, SERVICE_ID, USER_ID)
VALUES ('abbb2f3e-1234-4343-9194-badd9a882784', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', 'a1e71f3b-1234-4343-9194-badd9a882784');

INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
        'VOIRIE',
        'b6d71f3b-abc3-4343-9194-badd9a882784',
        'a1d71f3b-1234-4343-9194-badd9a882784',
        'rue de la mairie, 1');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb',
        'VOIRIE',
        'bd4c7ef2-77b7-457d-8ba1-370673ce1a7a',
        'a1e71f3b-1234-4343-9194-badd9a882784',
        'rue de la mairie, 1');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('cccccccccccccccccccccccccccccccccccc',
        'VOIRIE',
        '02ff9254-53ab-461e-a372-d8aa9695db5d',
        '$2a$10$sU0ar6aHnmZLbWIDOzfdMuYGk7oIREPM5K56N7GMObdMzPEU8V.tO',
        'rue de la mairie, 1');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('dddddddddddddddddddddddddddddddddddd',
        'VOIRIE',
        '2af7c53c-d43a-4912-8a77-97af82fb020c',
        '$2a$10$sU0ar6aHnmZLbWIDOzfdMuYGk7oIREPM5K56N7GMObdMzPEU8V.tO',
        'rue de la mairie, 1');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee',
        'VOIRIE',
        '38e31b98-c42d-4967-9ca8-127abf8785bf',
        '$2a$10$sU0ar6aHnmZLbWIDOzfdMuYGk7oIREPM5K56N7GMObdMzPEU8V.tO',
        'rue de la mairie, 1');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('ffffffffffffffffffffffffffffffffffff',
        'VOIRIE',
        'a7e39b8a-595a-4a65-a5ed-aa99073b765b',
        '$2a$10$sU0ar6aHnmZLbWIDOzfdMuYGk7oIREPM5K56N7GMObdMzPEU8V.tO',
        'rue de la mairie, 1');


INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('111111111111111111111111111111111111',
        'ADMINISTRATION DE LA VILLE',
        'b6d71f3b-abc3-4343-9194-badd9a882784',
        'b6d71f3b-1234-4343-9194-badd9a882784',
        'rue de la mairie, 2');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('222222222222222222222222222222222222',
        'ADMINISTRATION DE LA VILLE',
        'bd4c7ef2-77b7-457d-8ba1-370673ce1a7a',
        'b6d71f3b-4321-4343-9194-badd9a882784',
        'rue de la mairie, 2');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('333333333333333333333333333333333333',
        'ADMINISTRATION DE LA VILLE',
        '02ff9254-53ab-461e-a372-d8aa9695db5d',
        'b6d71f3b-1234-4343-9194-badd9a882784',
        'rue de la mairie, 2');

INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('444444444444444444444444444444444444',
        'ADMINISTRATION DE LA VILLE',
        '2af7c53c-d43a-4912-8a77-97af82fb020c',
        'b6d71f3b-4321-4343-9194-badd9a882784',
        'rue de la mairie, 2');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('555555555555555555555555555555555555',
        'ADMINISTRATION DE LA VILLE',
        '38e31b98-c42d-4967-9ca8-127abf8785bf',
        'b6d71f3b-4321-4343-9194-badd9a882784',
        'rue de la mairie, 2');
INSERT INTO SERVICE (SERVICE_ID, NAME, TOWN_ID, ADMINISTRATOR, ADDRESS)
VALUES ('666666666666666666666666666666666666',
        'ADMINISTRATION DE LA VILLE',
        'a7e39b8a-595a-4a65-a5ed-aa99073b765b',
        'b6d71f3b-4321-4343-9194-badd9a882784',
        'rue de la mairie, 2');


INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUMBER, BIRTH_DATE, EMAIL, PASSWORD, REG_NAT)
VALUES ('b6d71f3b-1234-4343-9194-badd9a882784',
        'Jhon',
        'Smith',
        '123 rue de l Olympe',
        13456789,
        '8rKfwJM5PvYztyZfUcWfqQ',
        'smitty22@gmail.com',
        '$2a$10$qNKn877j75G9h0Y3qHoHdO8YnQyDPv8PrHT3obNTircFFjpBN83Ru',
        '');

INSERT INTO EMPLOYEE (EMPLOYEE_ID, SERVICE_ID, USER_ID)
VALUES ('a1d72f3b-1234-4343-9194-badd9a882794', '111111111111111111111111111111111111', 'b6d71f3b-1234-4343-9194-badd9a882784');

INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUMBER, BIRTH_DATE, EMAIL, PASSWORD, REG_NAT)
VALUES ('b6d71f3b-4321-4343-9194-badd9a882784',
        'Jhon',
        'Doe',
        '123 bis rue de l Olympe',
        13456798,
        'HNPmStMHBTGH9o1APcOYig',
        'billythekid@gmail.com',
        '$2a$10$qNKn877j75G9h0Y3qHoHdO8YnQyDPv8PrHT3obNTircFFjpBN83Ru',
        '');
INSERT INTO EMPLOYEE (EMPLOYEE_ID, SERVICE_ID, USER_ID)
VALUES ('a1d72f3b-4321-4343-9194-badd9a882784', '222222222222222222222222222222222222', 'b6d71f3b-4321-4343-9194-badd9a882784');


INSERT INTO TOWN (TOWN_ID, NAME, TOWN_LANGUAGE, POSTAL_CODE, COUNTRY, ADDRESS, EMAIL, PHONE_NUMBER, LOGO_PATH, MAYOR_ID)
VALUES ('b6d71f3b-abc3-4343-9194-badd9a882784',
        'LESSINES',
        'FR',
        7860,
        'BE',
        'ADDRESS LESSINES',
        'LESSINES@be.be',
        '123456789',
        'C:\\DEV\\logo.jpg',
        'b6d71f3b-1234-4343-9194-badd9a882784');
INSERT INTO TOWN (TOWN_ID, NAME, TOWN_LANGUAGE, POSTAL_CODE, COUNTRY, ADDRESS, EMAIL, PHONE_NUMBER, LOGO_PATH, MAYOR_ID)
VALUES ('bd4c7ef2-77b7-457d-8ba1-370673ce1a7a',
        'MONS',
        'FR',
        7000,
        'BE',
        'ADDRESS MONS',
        'MONS@be.be',
        '123456789',
        'C:\\DEV\\logo.jpg',
        'b6d71f3b-1234-4343-9194-badd9a882784');
INSERT INTO TOWN (TOWN_ID, NAME, TOWN_LANGUAGE, POSTAL_CODE, COUNTRY, ADDRESS, EMAIL, PHONE_NUMBER, LOGO_PATH, MAYOR_ID)
VALUES ('02ff9254-53ab-461e-a372-d8aa9695db5d',
        'DINANT',
        'FR',
        5500,
        'BE',
        'ADDRESS DINANT',
        'DINANT@be.be',
        '123456789',
        'C:\\DEV\\logo.jpg',
        'b6d71f3b-1234-4343-9194-badd9a882784');
INSERT INTO TOWN (TOWN_ID, NAME, TOWN_LANGUAGE, POSTAL_CODE, COUNTRY, ADDRESS, EMAIL, PHONE_NUMBER, LOGO_PATH, MAYOR_ID)
VALUES ('2af7c53c-d43a-4912-8a77-97af82fb020c',
        'LA ROCHE',
        'FR',
        6980,
        'BE',
        'ADDRESS LA ROCHE',
        'LAROCHE@be.be',
        '123456789',
        'C:\\DEV\\logo.jpg',
        'b6d71f3b-4321-4343-9194-badd9a882784');
INSERT INTO TOWN (TOWN_ID, NAME, TOWN_LANGUAGE, POSTAL_CODE, COUNTRY, ADDRESS, EMAIL, PHONE_NUMBER, LOGO_PATH, MAYOR_ID)
VALUES ('38e31b98-c42d-4967-9ca8-127abf8785bf',
        'ENGHIEN',
        'FR',
        7850,
        'BE',
        'ADDRESS ENGHIEN',
        'ENGHIEN@be.be',
        '123456789',
        'C:\\DEV\\logo.jpg',
        'b6d71f3b-4321-4343-9194-badd9a882784');
INSERT INTO TOWN (TOWN_ID, NAME, TOWN_LANGUAGE, POSTAL_CODE, COUNTRY, ADDRESS, EMAIL, PHONE_NUMBER, LOGO_PATH, MAYOR_ID)
VALUES ('a7e39b8a-595a-4a65-a5ed-aa99073b765b',
        'BOUSVAL',
        'FR',
        1470,
        'BE',
        'ADDRESS BOUSVAL',
        'BOUSVAL@be.be',
        '123456789',
        'C:\\DEV\\logo.jpg',
        'b6d71f3b-4321-4343-9194-badd9a882784');

INSERT INTO ROLE (EMAIL, RIGHTS)
VALUES ('billythekid@gmail.com', 'MAYOR;EMPLOYEE');
INSERT INTO ROLE (EMAIL, RIGHTS)
VALUES ('smitty22@gmail.com', 'MAYOR;EMPLOYEE');
INSERT INTO ROLE (EMAIL, RIGHTS)
VALUES ('themoonwalker@gmail.com', 'EMPLOYEE');
INSERT INTO ROLE (EMAIL, RIGHTS)
VALUES ('themoonwalkette@gmail.com', 'EMPLOYEE');

INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUMBER, BIRTH_DATE, EMAIL, PASSWORD, REG_NAT)
VALUES ('fdretgfd-1234-4343-9194-badd9a882784',
        'test',
        'test',
        '321 test',
        13456789,
        '8rKfwJM5PvYztyZfUcWfqQ',
        'test@gmail.com',
        '$2a$10$qNKn877j75G9h0Y3qHoHdO8YnQyDPv8PrHT3obNTircFFjpBN83Ru',
        '');
INSERT INTO CITIZEN (CITIZEN_ID, TOWN, USER_ID)
VALUES ('abbb2f3e-12cc-4343-9194-badd9a882784', 'b6d71f3b-abc3-4343-9194-badd9a882784', 'fdretgfd-1234-4343-9194-badd9a882784');

INSERT INTO ROLE (EMAIL, RIGHTS)
VALUES ('test@gmail.com', 'CITIZEN');


INSERT INTO DEMAND (DEMAND_ID, NAME, SERVICE_ID, CREATOR, VERIFICATOR,COMMUNAL_NAME)
VALUES ('hgghgghgghgghgghgghgghgghgghgghgghgg',
        'test',
        'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
        'fdretgfd-1234-4343-9194-badd9a882784',
        'a1d71f3b-1234-4343-9194-badd9a882784','COM NAME');

INSERT INTO EVENT (EVENT_ID, USER_ID, COMMENT, DEMAND_ID, STATUS, CREATION_DATE)
VALUES ('hgghgshgghgghgghgghgghgghgghgghgghgg',
        'fdretgfd-1234-4343-9194-badd9a882784',
        'test create demand',
        'hgghgghgghgghgghgghgghgghgghgghgghgg',
        'NEW',
        '20180818');

INSERT INTO EVENT (EVENT_ID, USER_ID, COMMENT, DEMAND_ID, STATUS, CREATION_DATE)
VALUES ('hgghgshgghgghggddghgghgghgghgghgghgg',
        'a1d71f3b-1234-4343-9194-badd9a882784',
        'response pending demand',
        'hgghgghgghgghgghgghgghgghgghgghgghgg',
        'PENDING',
        '20180819');

INSERT INTO EVENT (EVENT_ID, USER_ID, COMMENT, DEMAND_ID, STATUS, CREATION_DATE)
VALUES ('hgghgshgghgghggddghggh123gghgghgghgg',
        'a1d71f3b-1234-4343-9194-badd9a882784',
        'response ACC demand',
        '08fd9c12-30b7-418d-9348-df49607d2d59	',
        'ACCEPTED',
        '20180822');


