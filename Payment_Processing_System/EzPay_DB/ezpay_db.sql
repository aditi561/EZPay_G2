-- "users" table which represents all users
CREATE TABLE users (
    userId INT PRIMARY KEY,
    userName VARCHAR(100) NOT NULL,
    emailId VARCHAR(100) NOT NULL
);
-- "bank_accounts" which represents all the bank accounts
CREATE TABLE bank_accounts (
    bankid INT PRIMARY KEY,
    bankName VARCHAR(100) NOT NULL,
    accountNumber VARCHAR(30) UNIQUE NOT NULL,
    isVerified BOOLEAN NOT NULL,
    userId INT, 
    FOREIGN KEY (userId) REFERENCES users(userId)
);
-- "transfers" table which represents all the transfers made
CREATE TABLE transfers (
    transferId INT PRIMARY KEY,
    senderAccountNumber VARCHAR(30) NOT NULL,
    receiverAccountNumber VARCHAR(30) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    transferDateTime TIMESTAMP NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (senderAccountNumber) REFERENCES bank_accounts(accountNumber),
    FOREIGN KEY (receiverAccountNumber) REFERENCES bank_accounts(accountNumber)
);
-- Inserting values to "users" table
INSERT INTO users (userId, userName, emailId) VALUES
(1, 'Rahul Verma', 'rahul.verma@example.com'),
(2, 'Sneha Kapoor', 'sneha.kapoor@example.com'),
(3, 'Vikram Singh', 'vikram.singh@example.com'),
(4, 'Meera Nair', 'meera.nair@example.com');

-- Inserting values to "bank_accounts" table
INSERT INTO bank_accounts (bankId, bankName, accountNumber, isVerified, userId) VALUES
(101, 'HDFC', 'ACC123456', TRUE, 1),
(102, 'ICICI', 'ACC234567', TRUE, 1),
(103, 'SBI', 'ACC345678', FALSE, 2),
(104, 'Axis', 'ACC456789', TRUE, 3),
(105, 'Kotak', 'ACC567890', TRUE, 4);

-- Inserting values to "transfers" table
INSERT INTO transfers (transferId, senderAccountNumber, receiverAccountNumber, amount, transferDateTime, status) VALUES
(1001, 'ACC123456', 'ACC234567', 5000.00, '2025-07-30 10:00:00', TRUE),
(1002, 'ACC234567', 'ACC345678', 1500.50, '2025-07-30 11:15:00', TRUE),
(1003, 'ACC345678', 'ACC456789', 2500.75, '2025-07-31 09:30:00', FALSE),
(1004, 'ACC456789', 'ACC567890', 3200.00, '2025-07-31 12:45:00', TRUE);

-- Describing "users" table
DESC users; 
-- Displaying "users" table data
SELECT * FROM users;

-- Describing "bank_accounts" table
DESC bank_accounts;
-- Displaying "bank_accounts" table data
SELECT * FROM bank_accounts;

-- Describing "transfers" table
DESC transfers;
-- Displaying "transfers" table data
SELECT * FROM transfers;