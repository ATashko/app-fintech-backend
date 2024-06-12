
CREATE DATABASE IF NOT EXISTS mstransactions;

USE mstransactions;

-- Crear tabla USER_ACCOUNT
CREATE TABLE USER_ACCOUNT (
    account_number VARCHAR(255) PRIMARY KEY,
    amount FLOAT DEFAULT 0,
    currency ENUM('AOA', 'AUD', 'GBP', 'EUR', 'KMF', 'USD', 'KYD', 'ILS', 'XOF', 'SSP', 'NZD', 'EGP', 'LRD',
                  'SBD', 'UZS', 'ZWL', 'XAF', 'AED', 'ZMW', 'KHR', 'VND', 'XPF', 'ZAR', 'GYD', 'MZN', 'TMT',
                  'LAK', 'HUF', 'ARS', 'TRY', 'HTG', 'SYP', 'SEK', 'BZD', 'CLP', 'RSD', 'DZD', 'BOB', 'CKD',
                  'VUV', 'NAD', 'YER', 'MOP', 'MUR', 'AWG', 'KGS', 'GTQ', 'TOP', 'GNF', 'TTD', 'JMD', 'BSD',
                  'RUB', 'LSL', 'MMK', 'BTN', 'MRU', 'CNY', 'PYG', 'DKK', 'NOK', 'GHS', 'MWK', 'PGK', 'SZL',
                  'JOD', 'CAD', 'MAD', 'MNT', 'ISK', 'THB', 'BGN', 'NIO', 'GIP', 'XCD', 'COP', 'BRL', 'NPR',
                  'KPW', 'ANG', 'SRD', 'KWD', 'SOS', 'CDF', 'ALL', 'HNL', 'SDG', 'HKD', 'LKR', 'BDT', 'MGA',
                  'BHD', 'MKD', 'UAH', 'ERN', 'AZN', 'SLL', 'DJF', 'UYU', 'GMD', 'MDL', 'AFN', 'BND', 'FJD',
                  'MXN', 'CHF', 'RWF', 'BMD', 'TWD', 'JPY', 'QAR', 'BWP', 'LBP', 'LYD', 'MYR', 'CRC', 'BAM',
                  'BIF', 'CVE', 'OMR', 'BYN', 'BBD', 'TZS', 'WST', 'ETB', 'INR', 'MVR', 'SCR', 'UGX', 'FKP',
                  'STN', 'IQD', 'PLN', 'TJS', 'KES', 'KZT', 'PAB', 'SGD', 'NGN', 'RON', 'CZK', 'AMD', 'VES',
                  'PHP', 'PEN', 'KRW', 'DOP', 'IRR', 'CUC', 'SAR', 'GEL', 'PKR', 'SHP', 'TND', 'IDR'),
    type_account ENUM('BALANCE', 'SAVING'),
    user_id VARCHAR(255) NOT NULL
);

-- Crear tabla Transaction
CREATE TABLE Transactions (
    transaction_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shipping_currency ENUM('AOA', 'AUD', 'GBP', 'EUR', 'KMF', 'USD', 'KYD', 'ILS', 'XOF', 'SSP', 'NZD', 'EGP', 'LRD',
                           	'SBD', 'UZS', 'ZWL', 'XAF', 'AED', 'ZMW', 'KHR', 'VND', 'XPF', 'ZAR', 'GYD', 'MZN', 'TMT',
                           	'LAK', 'HUF', 'ARS', 'TRY', 'HTG', 'SYP', 'SEK', 'BZD', 'CLP', 'RSD', 'DZD', 'BOB', 'CKD',
                           	'VUV', 'NAD', 'YER', 'MOP', 'MUR', 'AWG', 'KGS', 'GTQ', 'TOP', 'GNF', 'TTD', 'JMD', 'BSD',
                           	'RUB', 'LSL', 'MMK', 'BTN', 'MRU', 'CNY', 'PYG', 'DKK', 'NOK', 'GHS', 'MWK', 'PGK', 'SZL',
                           	'JOD', 'CAD', 'MAD', 'MNT', 'ISK', 'THB', 'BGN', 'NIO', 'GIP', 'XCD', 'COP', 'BRL', 'NPR',
                           	'KPW', 'ANG', 'SRD', 'KWD', 'SOS', 'CDF', 'ALL', 'HNL', 'SDG', 'HKD', 'LKR', 'BDT', 'MGA',
                           	'BHD', 'MKD', 'UAH', 'ERN', 'AZN', 'SLL', 'DJF', 'UYU', 'GMD', 'MDL', 'AFN', 'BND', 'FJD',
                           	'MXN', 'CHF', 'RWF', 'BMD', 'TWD', 'JPY', 'QAR', 'BWP', 'LBP', 'LYD', 'MYR', 'CRC', 'BAM',
                           	'BIF', 'CVE', 'OMR', 'BYN', 'BBD', 'TZS', 'WST', 'ETB', 'INR', 'MVR', 'SCR', 'UGX', 'FKP',
                           	'STN', 'IQD', 'PLN', 'TJS', 'KES', 'KZT', 'PAB', 'SGD', 'NGN', 'RON', 'CZK', 'AMD', 'VES',
                           	'PHP', 'PEN', 'KRW', 'DOP', 'IRR', 'CUC', 'SAR', 'GEL', 'PKR', 'SHP', 'TND', 'IDR'),
    receipt_currency ENUM('AOA', 'AUD', 'GBP', 'EUR', 'KMF', 'USD', 'KYD', 'ILS', 'XOF', 'SSP', 'NZD', 'EGP', 'LRD',
                          'SBD', 'UZS', 'ZWL', 'XAF', 'AED', 'ZMW', 'KHR', 'VND', 'XPF', 'ZAR', 'GYD', 'MZN', 'TMT',
                          'LAK', 'HUF', 'ARS', 'TRY', 'HTG', 'SYP', 'SEK', 'BZD', 'CLP', 'RSD', 'DZD', 'BOB', 'CKD',
                          'VUV', 'NAD', 'YER', 'MOP', 'MUR', 'AWG', 'KGS', 'GTQ', 'TOP', 'GNF', 'TTD', 'JMD', 'BSD',
                          'RUB', 'LSL', 'MMK', 'BTN', 'MRU', 'CNY', 'PYG', 'DKK', 'NOK', 'GHS', 'MWK', 'PGK', 'SZL',
                          'JOD', 'CAD', 'MAD', 'MNT', 'ISK', 'THB', 'BGN', 'NIO', 'GIP', 'XCD', 'COP', 'BRL', 'NPR',
                          'KPW', 'ANG', 'SRD', 'KWD', 'SOS', 'CDF', 'ALL', 'HNL', 'SDG', 'HKD', 'LKR', 'BDT', 'MGA',
                          'BHD', 'MKD', 'UAH', 'ERN', 'AZN', 'SLL', 'DJF', 'UYU', 'GMD', 'MDL', 'AFN', 'BND', 'FJD',
                          'MXN', 'CHF', 'RWF', 'BMD', 'TWD', 'JPY', 'QAR', 'BWP', 'LBP', 'LYD', 'MYR', 'CRC', 'BAM',
                          'BIF', 'CVE', 'OMR', 'BYN', 'BBD', 'TZS', 'WST', 'ETB', 'INR', 'MVR', 'SCR', 'UGX', 'FKP',
                          'STN', 'IQD', 'PLN', 'TJS', 'KES', 'KZT', 'PAB', 'SGD', 'NGN', 'RON', 'CZK', 'AMD', 'VES',
                           'PHP', 'PEN', 'KRW', 'DOP', 'IRR', 'CUC', 'SAR', 'GEL', 'PKR', 'SHP', 'TND', 'IDR'),
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


