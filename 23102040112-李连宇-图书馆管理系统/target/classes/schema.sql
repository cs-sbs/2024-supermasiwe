CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE books (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publish_date DATE NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(20) NOT NULL,
    extra_info JSON
);

-- 插入管理员账号
INSERT INTO users (username, password, is_admin) VALUES ('admin', 'admin123', true); 