-- ========================================
-- Maybank Assessment Application
-- Database Setup Script for MSSQL Server
-- ========================================

-- Create database if it doesn't exist
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'TESTDB')
BEGIN
    CREATE DATABASE TESTDB;
    PRINT 'Database TESTDB created successfully.';
END
ELSE
BEGIN
    PRINT 'Database TESTDB already exists.';
END
GO

-- Switch to TESTDB
USE TESTDB;
GO

-- ========================================
-- Note: Tables will be auto-created by Hibernate
-- based on JPA entities when application starts
-- ========================================

-- Optional: Create tables manually if needed
-- (Spring Boot will auto-create these with spring.jpa.hibernate.ddl-auto=update)

-- Sample data for testing (Optional)
-- You can insert this data after the application creates the tables

/*
-- Insert sample products
INSERT INTO products (name, description, price, stock_quantity, category, active, created_at, updated_at)
VALUES 
('Laptop Dell XPS 15', 'High-performance laptop with Intel i7 processor', 5499.99, 50, 'Electronics', 1, GETDATE(), GETDATE()),
('iPhone 15 Pro', 'Latest Apple smartphone with A17 chip', 4999.99, 100, 'Electronics', 1, GETDATE(), GETDATE()),
('Samsung Galaxy S24', 'Premium Android smartphone', 3999.99, 75, 'Electronics', 1, GETDATE(), GETDATE()),
('Sony WH-1000XM5', 'Noise-canceling wireless headphones', 1299.99, 200, 'Electronics', 1, GETDATE(), GETDATE()),
('MacBook Pro 16', 'Professional laptop with M3 chip', 9999.99, 30, 'Electronics', 1, GETDATE(), GETDATE()),
('iPad Air', 'Versatile tablet for work and play', 2499.99, 150, 'Electronics', 1, GETDATE(), GETDATE()),
('Apple Watch Series 9', 'Advanced smartwatch with health features', 1799.99, 120, 'Electronics', 1, GETDATE(), GETDATE()),
('AirPods Pro', 'Wireless earbuds with active noise cancellation', 999.99, 300, 'Electronics', 1, GETDATE(), GETDATE()),
('Samsung 4K TV 65"', 'Ultra HD Smart TV with HDR', 3499.99, 40, 'Electronics', 1, GETDATE(), GETDATE()),
('Canon EOS R6', 'Professional mirrorless camera', 8999.99, 25, 'Electronics', 1, GETDATE(), GETDATE()),
('Nintendo Switch', 'Hybrid gaming console', 1299.99, 180, 'Electronics', 1, GETDATE(), GETDATE()),
('PlayStation 5', 'Next-gen gaming console', 2199.99, 60, 'Electronics', 1, GETDATE(), GETDATE()),
('Xbox Series X', 'Powerful gaming console', 2199.99, 70, 'Electronics', 1, GETDATE(), GETDATE()),
('Bose QuietComfort 45', 'Premium noise-canceling headphones', 1499.99, 90, 'Electronics', 1, GETDATE(), GETDATE()),
('GoPro Hero 12', 'Action camera for adventures', 1899.99, 110, 'Electronics', 1, GETDATE(), GETDATE());
*/

PRINT 'Database setup complete.';
PRINT 'Start the Spring Boot application to auto-create tables.';
GO

-- ========================================
-- Verify database connection
-- ========================================
SELECT 'Database TESTDB is ready!' AS Status;
GO
