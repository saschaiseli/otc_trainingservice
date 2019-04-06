CREATE TABLE ATHLETE
(
    id           bigint       NOT NULL PRIMARY KEY,
    birthday     date         DEFAULT NULL,
    email        varchar(255) NOT NULL,
    firstName    varchar(255) NOT NULL,
    lastLogin    timestamp    DEFAULT NULL,
    lastName     varchar(255) NOT NULL,
    maxheartrate int          DEFAULT NULL,
    password     varchar(255) NOT NULL,
    speed        varchar(255) DEFAULT NULL,
    unit         varchar(255) DEFAULT NULL
);

INSERT INTO ATHLETE (id, birthday, email, firstName, lastLogin, lastName, maxheartrate, password,
                     speed, unit)
VALUES (1, NULL, 'sascha.iseli@gmx.ch', 'sascha', NULL, 'iseli', 0,
        '$2a$10$ZdF9OFvLpERHAgpNPfTbAeQ.U/s6WvHlDcKw8Hg.XaZiLNRt63MLG', NULL, NULL);

CREATE TABLE HEALTH
(
    id            bigint NOT NULL PRIMARY KEY,
    cardio        int    DEFAULT NULL,
    dateofmeasure date   DEFAULT NULL,
    weight        int    DEFAULT NULL,
    ID_FK_ATHLETE bigint DEFAULT NULL
);

CREATE TABLE LAP_INFO
(
    id              bigint NOT NULL PRIMARY KEY,
    LAP_END         int          DEFAULT NULL,
    geschwindigkeit varchar(255) DEFAULT NULL,
    heartBeat       int    NOT NULL,
    lap             int    NOT NULL,
    pace            varchar(255) DEFAULT NULL,
    LAP_START       int          DEFAULT NULL,
    time            bigint NOT NULL
);

CREATE TABLE PLANING_WEEK
(
    id            bigint  NOT NULL PRIMARY KEY,
    hasInterval   boolean NOT NULL,
    jahr          int    DEFAULT NULL,
    kmprowoche    int    DEFAULT NULL,
    kw            int    DEFAULT NULL,
    langerLauf    int    DEFAULT NULL,
    ID_FK_ATHLETE bigint DEFAULT NULL
);

CREATE TABLE SHOE
(
    id            bigint       NOT NULL PRIMARY KEY,
    imageicon     varchar(255) DEFAULT NULL,
    kaufdatum     timestamp    DEFAULT NULL,
    preis         int          NOT NULL,
    schuhname     varchar(255) NOT NULL,
    ID_FK_ATHLETE bigint       NOT NULL
);

CREATE TABLE TRACK_PROPERTIES
(
    id             bigint NOT NULL PRIMARY KEY,
    altitude       int    NOT NULL,
    distance       float  NOT NULL,
    heartbeat      int    NOT NULL,
    lap            int    NOT NULL,
    latitude       float  DEFAULT NULL,
    longitude      float  DEFAULT NULL,
    zeit           bigint NOT NULL,
    id_fk_training bigint DEFAULT NULL
);

CREATE TABLE TRAINING
(
    id                      bigint NOT NULL PRIMARY KEY,
    startInMillis           bigint NOT NULL,
    ANAEROB_TRAINING_EFFECT int          DEFAULT NULL,
    averageHeartBeat        int    NOT NULL,
    dateOfImport            date   NOT NULL,
    dateOfStart             timestamp    DEFAULT NULL,
    dauer                   bigint NOT NULL,
    downMeter               int          DEFAULT NULL,
    GEO_QUALITY             int          DEFAULT NULL,
    fileName                varchar(255) DEFAULT NULL,
    GEO_JSON                text         DEFAULT NULL,
    laengeInMeter           bigint NOT NULL,
    maxHeartBeat            int    NOT NULL,
    maxSpeed                float  NOT NULL,
    note                    varchar(255) DEFAULT NULL,
    sport                   varchar(255) DEFAULT NULL,
    TRAINING_EFFECT         int          DEFAULT NULL,
    trainingType            varchar(255) DEFAULT NULL,
    upMeter                 int          DEFAULT NULL,
    ID_FK_ATHLETE           bigint NOT NULL
);

CREATE TABLE TRAINING_GOAL
(
    id             bigint  NOT NULL PRIMARY KEY,
    active         boolean NOT NULL,
    derStart       date    NOT NULL,
    currentValue   float   NOT NULL,
    distanceOrHour int     NOT NULL,
    dasEnde        date         DEFAULT NULL,
    name           varchar(255) DEFAULT NULL,
    prediction     float   NOT NULL,
    unit           varchar(255) DEFAULT NULL,
    ID_FK_ATHLETE  bigint  NOT NULL
);

CREATE TABLE TRAINING_LAP_INFO
(
    TRAINING_id bigint NOT NULL,
    lapInfos_id bigint NOT NULL
);


CREATE TABLE WEATHER
(
    id        bigint NOT NULL PRIMARY KEY,
    imageicon varchar(255) DEFAULT NULL,
    wetter    varchar(255) DEFAULT NULL
);

ALTER TABLE ATHLETE
    ADD CONSTRAINT UK_57whiaipl4iqd03ijo7tu68t UNIQUE (email);

ALTER TABLE TRAINING_LAP_INFO
    ADD CONSTRAINT UK_had12x4hecmujggo74tk4c97q UNIQUE (lapInfos_id),
    ADD CONSTRAINT FK2cvrkg1rswdmdk4pjeo2dkxwc FOREIGN KEY (TRAINING_id)
        REFERENCES TRAINING (id),
    ADD CONSTRAINT FKhbu3g825qtk1w1il3dywtsg1k FOREIGN KEY (lapInfos_id)
        REFERENCES LAP_INFO (id);

ALTER TABLE HEALTH
    ADD CONSTRAINT FK7aighiwst0rhtcm2udijwcc7e FOREIGN KEY (ID_FK_ATHLETE)
        REFERENCES ATHLETE (id);

ALTER TABLE PLANING_WEEK
    ADD CONSTRAINT FK20496k1n0vcwe23oed6mudj58 FOREIGN KEY (ID_FK_ATHLETE)
        REFERENCES ATHLETE (id);

ALTER TABLE SHOE
    ADD CONSTRAINT FKiks1o69lfm5abik0wv65lijxr FOREIGN KEY (ID_FK_ATHLETE)
        REFERENCES ATHLETE (id);

ALTER TABLE TRACK_PROPERTIES
    ADD CONSTRAINT FK3y6lu5a71eqivg79yk7b4h123 FOREIGN KEY (id_fk_training)
        REFERENCES TRAINING (id);

ALTER TABLE TRAINING
    ADD CONSTRAINT FK8lciiu1wo4xgxt12slap6qayg FOREIGN KEY (ID_FK_ATHLETE)
        REFERENCES ATHLETE (id);

ALTER TABLE TRAINING_GOAL
    ADD CONSTRAINT FKa2tuf8vhwrfqidsxnhnip70hx FOREIGN KEY (ID_FK_ATHLETE)
        REFERENCES ATHLETE (id);