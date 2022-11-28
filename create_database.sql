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
    inspected        BIT(1),
    created_at       DATETIME(6)        NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at       DATETIME(6)        NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Mads
CREATE TABLE IF NOT EXISTS subscriptions
(
    name       VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,
    days       BIGINT                   NOT NULL,
    price      DOUBLE                   NOT NULL,
    available  BIT(1),
    created_at DATETIME(6)              NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6)              NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Nicolai (one-to-one, bookings)
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
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
INSERT INTO address (street, city, zipCode, country)
VALUES ('Street 1A', 'City A', '42311', 'Denmark');
INSERT INTO address (street, city, zipCode, country)
VALUES ('Street 1B', 'City B', '32545', 'Denmark');
INSERT INTO address (street, city, zipCode, country)
VALUES ('Street 1C', 'City C', '14324', 'Denmark');
INSERT INTO address (street, city, zipCode, country)
VALUES ('Street 1D', 'City D', '53463', 'Denmark');

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
    delivered_at      DATETIME(6),
    updated_at        DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at        DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (subscription_name) REFERENCES subscriptions (name),
    FOREIGN KEY (pickup_point_id) REFERENCES pickup_points (id),
    FOREIGN KEY (vehicle_number) REFERENCES cars (vehicle_number),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Nicolai (one-to-one, bookings)
CREATE TABLE IF NOT EXISTS invoices
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT,
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
    CONSTRAINT booking_damage_report
        FOREIGN KEY (booking_id) REFERENCES bookings (id)
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_specifications
(
    description VARCHAR(255) PRIMARY KEY,
    damaged     BIT(1),
    price       DOUBLE,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_report_specifications
(
    report_id        BIGINT,
    spec_description VARCHAR(255),
    updated_at       DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at       DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (report_id, spec_description),
    FOREIGN KEY (report_id) REFERENCES damage_reports (booking_id),
    FOREIGN KEY (spec_description) REFERENCES damage_specifications (description)
);