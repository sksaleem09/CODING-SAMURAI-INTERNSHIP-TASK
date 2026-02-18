-- Library Management System Database Setup
-- Run this script in MySQL before running the application

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS books (
    book_id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available'
);

-- Sample book data (optional)
INSERT INTO books (book_id, title, author, status) VALUES 
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Available'),
(2, 'To Kill a Mockingbird', 'Harper Lee', 'Available'),
(3, 'Java Programming', 'Bjarne Stroustrup', 'Borrowed');
