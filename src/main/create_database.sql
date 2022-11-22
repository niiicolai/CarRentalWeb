DROP DATABASE IF EXISTS carrental;
CREATE DATABASE carrental;

CREATE TABLE cars (
    vehicle_number      LONG            PRIMARY KEY         NOT NULL      AUTO_INCREMENT,
    frame_number        VARCHAR(255)    UNIQUE,
    brand               VARCHAR(255),
    model               VARCHAR(255),
    color               VARCHAR(255),
    equipment_level     INTEGER,
    steel_price         DOUBLE,
    registration_fee    DOUBLE,
    co2_discharge       DOUBLE,
    inspected           BIT(1),
    booking             BIGINT,
    created_at          DATE,
    updated_at          DATE
);