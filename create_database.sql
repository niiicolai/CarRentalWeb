CREATE DATABASE IF NOT EXISTS carrental;
USE carrental;
-- Nicolai
CREATE TABLE IF NOT EXISTS users (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    username            VARCHAR(255) NOT NULL UNIQUE,
    password            VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL,
    enabled             INT NOT NULL,
    account_non_locked  INT NOT NULL,
    account_non_expired INT NOT NULL,
    credentials_non_expired INT NOT NULL,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Nicolai
CREATE TABLE IF NOT EXISTS user_role (
    user_id             BIGINT,
    role_name           VARCHAR(255),
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
-- Mikkel
CREATE TABLE IF NOT EXISTS cars (
    vehicle_number      BIGINT          PRIMARY KEY         NOT NULL      AUTO_INCREMENT,
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
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Mads
CREATE TABLE IF NOT EXISTS subscriptions (
    name                VARCHAR(255)    PRIMARY KEY         NOT NULL      UNIQUE,
    days                BIGINT          NOT NULL, -- should be BIGINT because LocalDateTime.plusDays(days) expects a long. 
    price               DOUBLE          NOT NULL,
    available           BIT(1),
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Thomas
CREATE TABLE IF NOT EXISTS bookings (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id             BIGINT NOT NULL,
    vehicle_number      BIGINT NOT NULL,
    subscription_name   VARCHAR(255) NOT NULL,
    pickup_point_name   VARCHAR(255) NOT NULL,
    delivered_at        DATETIME,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(subscription_name) REFERENCES subscriptions(name),
    FOREIGN KEY(pickup_point_name) REFERENCES pickup_points(location_name),
    FOREIGN KEY(vehicle_number) REFERENCES cars(vehicle_number),
    FOREIGN KEY(user_id) REFERENCES users(id)
);
-- Nicolai (one-to-one, bookings)
CREATE TABLE IF NOT EXISTS credit_ratings (
    booking_id          BIGINT PRIMARY KEY,
    state               VARCHAR(255) NOT NULL,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(booking_id) REFERENCES bookings(booking_id)
);
-- Nicolai (one-to-one, bookings)
CREATE TABLE IF NOT EXISTS invoices (
    booking_id          BIGINT PRIMARY KEY,
    due_date            DATETIME NOT NULL,
    paid_at             DATETIME,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Nicolai
CREATE TABLE IF NOT EXISTS invoice_specifications (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    description         VARCHAR(255) NOT NULL,
    price               DOUBLE NOT NULL,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    booking_id          BIGINT NOT NULL,
    FOREIGN KEY(booking_id) REFERENCES invoices(booking_id)
);
-- Thomas
CREATE TABLE IF NOT EXISTS pickup_points  (
    location_name       VARCHAR(255) PRIMARY KEY NOT NULL,
    address             BIGINT,
    created_at          DATE,
    updated_at          DATE
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_reports (
    booking_id          BIGINT PRIMARY KEY,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT booking_damage_report
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_specifications (
    description         VARCHAR(255)    PRIMARY KEY         NOT NULL      UNIQUE,
    damaged             BIT(1),
    price               DOUBLE,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Mads
CREATE TABLE IF NOT EXISTS damage_report_specifications (
    report_id           BIGINT,
    spec_description    VARCHAR(255),
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (report_id, spec_description),
    FOREIGN KEY (report_id) REFERENCES damage_reports(id),
    FOREIGN KEY (spec_description) REFERENCES damage_specifications(description)
);