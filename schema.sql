CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE users (
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          ENUM('ADMIN', 'MEMBER') NOT NULL DEFAULT 'MEMBER'
);

CREATE TABLE books (
    book_id          INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(200) NOT NULL,
    author           VARCHAR(100) NOT NULL,
    category         VARCHAR(50),
    total_copies     INT NOT NULL,
    available_copies INT NOT NULL,
    CHECK (total_copies >= 0),
    CHECK (available_copies >= 0 AND available_copies <= total_copies)
);

CREATE TABLE transactions (
    txn_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    book_id     INT NOT NULL,
    issue_date  DATE NOT NULL,
    due_date    DATE NOT NULL,
    return_date DATE NULL,
    fine        DECIMAL(8,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

INSERT INTO users (name, email, password_hash, role) VALUES
    ('Admin', 'admin@lib.com', 'temp_hash_admin', 'ADMIN'),
    ('Aditya', 'aditya@lib.com', 'temp_hash_member', 'MEMBER');

INSERT INTO books (title, author, category, total_copies, available_copies) VALUES
    ('Effective Java', 'Joshua Bloch', 'Java', 3, 3),
    ('Clean Code', 'Robert C. Martin', 'Programming', 2, 2),
    ('Let Us C', 'Yashavant Kanetkar', 'C', 5, 5);
