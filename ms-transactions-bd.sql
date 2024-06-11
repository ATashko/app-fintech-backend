
CREATE DATABASE IF NOT EXISTS mstransactions;

USE mstransactions;

-- Crear tabla USER_ACCOUNT
CREATE TABLE USER_ACCOUNT (
    account_number VARCHAR(255) PRIMARY KEY,
    amount FLOAT DEFAULT 0,
    currency ENUM('ARS', 'COP', 'USD', 'EUR'),
    type_account ENUM('BALANCE', 'SAVING'),
    user_id VARCHAR(255) NOT NULL
);

-- Crear tabla Transaction
CREATE TABLE Transactions (
    transaction_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shipping_currency ENUM('ARS', 'COP', 'USD', 'EUR'),
    receipt_currency ENUM('ARS', 'COP', 'USD', 'EUR'),
    value_to_transfer FLOAT,
    rate FLOAT,
    rate_value FLOAT,
    transaction_total FLOAT,
    converted_transaction_total FLOAT,
    method_of_payment ENUM('CASH', 'TRIWALTRANSFER'),
    transfer_type ENUM('DEPOSIT', 'TRANSFER_TO_USER', 'TRANSFER_TO_MY_ACCOUNT'),
    name_of_recipient VARCHAR(50),
    user_id VARCHAR(50),
    username VARCHAR(50),
    email VARCHAR(100),
    transaction_details VARCHAR(255) ,
    account_number VARCHAR(255) ,
    CONSTRAINT FK_Transactions_user_account FOREIGN KEY (account_number)
    REFERENCES USER_ACCOUNT (account_number) ON DELETE CASCADE
);

-- Crear un índice en la tabla Transaction para buscar por account_number
CREATE INDEX idx_account_number ON Transactions (account_number);


