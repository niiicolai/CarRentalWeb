CREATE DATABASE IF NOT EXISTS carrental;
USE carrental;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       name VARCHAR(255) PRIMARY KEY,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_role (
                       user_id INT,
                       role_name VARCHAR(255),
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       PRIMARY KEY (user_id, role_name),
                       FOREIGN KEY (user_id) REFERENCES users(id),
                       FOREIGN KEY (role_name) REFERENCES roles(name)
);

CREATE TABLE cars (
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
                      created_at          DATE,
                      updated_at          DATE
);

CREATE TABLE subscriptions (
                      name                VARCHAR(255)    PRIMARY KEY         NOT NULL      UNIQUE,
                      days                DOUBLE,
                      price               DOUBLE,
                      available           BIT(1),
                      created_at          DATE,
                      updated_at          DATE
);


CREATE TABLE bookings (
                       id INT AUTO_INCREMENT PRIMARY KEY
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE credit_ratings (
                       booking_id INT AUTO_INCREMENT PRIMARY KEY,
                       state VARCHAR(255) NOT NULL,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY(booking_id) REFERENCES bookings(id)
);