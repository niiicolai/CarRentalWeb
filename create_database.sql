DROP DATABASE IF EXISTS carrental;

CREATE DATABASE IF NOT EXISTS carrental;
USE carrental;

-- Nicolai
CREATE TABLE IF NOT EXISTS users
(
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    username                VARCHAR(255) NOT NULL UNIQUE,
    password                VARCHAR(255) NOT NULL,
    email                   VARCHAR(255) NOT NULL,
    enabled                 INT          NOT NULL,
    account_non_locked      INT          NOT NULL,
    account_non_expired     INT          NOT NULL,
    credentials_non_expired INT          NOT NULL,
    updated_at              DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at              DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Nicolai
CREATE TABLE IF NOT EXISTS user_role
(
    user_id    BIGINT,
    role_name  VARCHAR(255),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
-- Mikkel
CREATE TABLE IF NOT EXISTS cars
(
    vehicle_number   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    frame_number     VARCHAR(255) UNIQUE,
    brand            VARCHAR(255),
    model            VARCHAR(255),
    color            VARCHAR(255),
    equipment_level  INTEGER,
    steel_price      DOUBLE,
    registration_fee DOUBLE,
    co2_discharge    DOUBLE,
    sell_price       DOUBLE,
    sold             BIT(1),
    inspected        BIT(1),
    damaged          BOOLEAN,
    first_rented_at  DATETIME(6),        
    created_at       DATETIME(6)        NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at       DATETIME(6)        NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Mads
CREATE TABLE IF NOT EXISTS subscriptions
(
    name       VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,
    days       DOUBLE                   NOT NULL,
    price      DOUBLE                   NOT NULL,
    available  BIT(1)                   NOT NULL,
    created_at DATETIME(6)              NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6)              NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Nicolai 
CREATE TABLE IF NOT EXISTS credit_ratings
(
    user_id    BIGINT PRIMARY KEY,
    state      VARCHAR(255) NOT NULL,
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
-- Thomas
CREATE TABLE IF NOT EXISTS address
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    street     VARCHAR(255) NOT NULL,
    city       VARCHAR(255) NOT NULL,
    zipCode    VARCHAR(255) NOT NULL,
    country    VARCHAR(255) NOT NULL,
    latitude   DOUBLE NOT NULL,
    longitude  DOUBLE NOT NULL,
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Thomas
CREATE TABLE IF NOT EXISTS pickup_points
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    address_id BIGINT       NOT NULL,
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (address_id) REFERENCES address (id)
);
-- Thomas
CREATE TABLE IF NOT EXISTS bookings
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT       NOT NULL,
    vehicle_number    BIGINT       NOT NULL,
    subscription_name VARCHAR(255) NOT NULL,
    pickup_point_id   BIGINT       NOT NULL,
    kilometer_driven  DOUBLE,
    delivered_at      DATETIME(6),
    returned_at       DATETIME(6),
    updated_at        DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at        DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (subscription_name) REFERENCES subscriptions (name),
    FOREIGN KEY (pickup_point_id) REFERENCES pickup_points (id),
    FOREIGN KEY (vehicle_number) REFERENCES cars (vehicle_number),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Nicolai
CREATE TABLE IF NOT EXISTS invoices
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    due_date   DATETIME(6) NOT NULL,
    paid_at    DATETIME(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (booking_id) REFERENCES bookings (id)
);
-- Nicolai
CREATE TABLE IF NOT EXISTS invoice_specifications
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price       DOUBLE       NOT NULL,
    updated_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    invoice_id  BIGINT       NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoices (id)
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_reports
(
    booking_id BIGINT PRIMARY KEY,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (booking_id) REFERENCES bookings (id)
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_specifications
(
    description VARCHAR(255) PRIMARY KEY,
    price       DOUBLE,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_report_specifications
(
    report_id        BIGINT NOT NULL,
    spec_description VARCHAR(255) NOT NULL,
    updated_at       DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at       DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (report_id) REFERENCES damage_reports (booking_id),
    FOREIGN KEY (spec_description) REFERENCES damage_specifications (description)
);
-- Example data
-- Mads
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('JTHKGJEC80N519573', 'Opel', 'Corsa', 'Orange', 4, 1500, 8000, 133, 150000, false, true, false);
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('AKLFDASD39H256126', 'Citroën', 'C5 Aircross PHEV', 'Marineblå', 5, 1500, 9000, 133, 140000, false, true, false);
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('HSFDAENL95J912464', 'Opel', 'Crossland', 'Grå', 2, 1500, 8500, 133, 120000, false, true, false);
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('FAOJDBVG26D481259', 'Citroën', 'C3 Aircross', 'Koksgrå', 3, 1500, 7000, 133, 110000, false, true, false);
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('HASDIKWY52M586732', 'Fiat', '500e', 'Lyseblå', 1, 1500, 6000, 0, 90000, false, true, false);
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('HKVCLKDH91F654284', 'Toyota', 'Aygo', 'Hvid', 3, 1500, 6500, 133, 95000, false, true, true);
INSERT INTO cars (frame_number, brand, model, color, equipment_level, steel_price, registration_fee, co2_discharge, sell_price, sold, inspected, damaged)
VALUES ('LKASDHBA23L175635', 'Suzuki', 'Swift', 'Rød', 1, 1500, 7500, 133, 105000, true, true, false);
-- Mads
INSERT INTO subscriptions (name, days, price, available)
VALUES ('4 måneder', 120, 19194, 1);
INSERT INTO subscriptions (name, days, price, available)
VALUES ('Unlimited (36 måneder)', 1096, 107964, 1);
-- Nicolai
INSERT INTO address (street, city, zipCode, country, latitude, longitude)
VALUES ('Street 1A', 'City A', '42311', 'Denmark', 55.696, 12.557);
INSERT INTO address (street, city, zipCode, country, latitude, longitude)
VALUES ('Street 1B', 'City B', '32545', 'Denmark', 55.696, 11.557);
INSERT INTO address (street, city, zipCode, country, latitude, longitude)
VALUES ('Street 1C', 'City C', '14324', 'Denmark', 54.696, 12.557);
INSERT INTO address (street, city, zipCode, country, latitude, longitude)
VALUES ('Street 1D', 'City D', '53463', 'Denmark', 55.696, 10.557);
-- Mads
INSERT INTO pickup_points (name, address_id)
VALUES ('Afhentnings sted 1', 1);
INSERT INTO pickup_points (name, address_id)
VALUES ('Afhentnings sted 2', 2);
INSERT INTO pickup_points (name, address_id)
VALUES ('Afhentnings sted 3', 3);
INSERT INTO pickup_points (name, address_id)
VALUES ('Afhentnings sted 4', 4);
-- Mads
INSERT INTO damage_specifications (description, price)
VALUES ('Bule', 2000);
INSERT INTO damage_specifications (description, price)
VALUES ('Ridse', 1000);
INSERT INTO damage_specifications (description, price)
VALUES ('Smadret rude', 3000);
INSERT INTO damage_specifications (description, price)
VALUES ('Ødelagt forsæde', 1500);
INSERT INTO damage_specifications (description, price)
VALUES ('Ødelagt bagsæde', 2500);