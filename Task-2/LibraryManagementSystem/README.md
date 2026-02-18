# Library Management System

A simple Java Swing-based library management desktop application with MySQL backend. Built for college/internship evaluation.

## Features

- **Login Module**: Secure login with hardcoded credentials (admin/admin)
- **Book Management**: Add, view, and manage books in the library
- **Borrow/Return System**: Update book status between "Available" and "Borrowed"
- **Real-time Database Operations**: All changes sync with MySQL database
- **Clean GUI**: Simple, beginner-friendly Swing interface

## Technologies Used

- **Java** - Core language
- **Swing** - GUI framework
- **JDBC** - Database connectivity with PreparedStatement
- **MySQL** - Database system

## Project Structure

```
LibraryManagementSystem/
├── src/
│   ├── app/
│   │   └── MainApp.java              (Entry point)
│   ├── gui/
│   │   ├── LoginFrame.java           (Login window)
│   │   └── LibraryDashboard.java     (Main application window)
│   ├── model/
│   │   └── Book.java                 (Book data model)
│   ├── dao/
│   │   └── BookDAO.java              (Database operations)
│   └── util/
│       └── DBConnection.java         (Database connection)
├── DATABASE_SETUP.sql                (Database schema)
└── README.md                         (This file)
```

## Setup Instructions

### 1. Database Setup

- Install MySQL Server
- Open MySQL command line or MySQL Workbench
- Run the `DATABASE_SETUP.sql` script:
  ```sql
  SOURCE path/to/DATABASE_SETUP.sql;
  ```
- This creates the `library_db` database with a `books` table

### 2. JDBC Driver Setup

- Download MySQL JDBC Driver (mysql-connector-java-x.x.x.jar)
- Copy it to your project's `lib/` directory OR add to classpath

### 3. Update Database Connection

Edit `src/util/DBConnection.java` if needed:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
```

### 4. Compile and Run

**Compile:**
```bash
cd src
javac -d ../bin util/*.java model/*.java dao/*.java gui/*.java app/*.java
```

**Run:**
```bash
cd ..
java -cp bin app.MainApp
```

Or use an IDE like Eclipse or IntelliJ IDEA to open the project.

## Usage

### Login
- Username: `admin`
- Password: `admin`

### Main Window Features

1. **Add Book**
   - Enter Book ID, Title, Author
   - Click "Add Book"
   - Status defaults to "Available"

2. **Borrow Book**
   - Select book from table
   - Click "Borrow Book"
   - Status changes to "Borrowed"

3. **Return Book**
   - Select borrowed book from table
   - Click "Return Book"
   - Status changes to "Available"

4. **Refresh Table**
   - Click "Refresh" to reload data from database

## Database Schema

```sql
CREATE TABLE books (
    book_id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available'
);
```

## Notes

- All database operations use JDBC PreparedStatement for security
- Simple layout using GridLayout and BorderLayout for beginners
- Error handling with JOptionPane dialogs
- No external dependencies except MySQL JDBC driver

## Setup Instructions

### 1. Database Setup
Execute the SQL commands from `DATABASE_SETUP.sql`:

```sql
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE books (
    book_id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE borrow_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    borrower_name VARCHAR(255) NOT NULL,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);
```

### 2. Configure Database Connection
Edit `src/util/DBConnection.java`:
- URL: `jdbc:mysql://localhost:3306/library_db`
- USER: `root`
- PASSWORD: `root`

### 3. Add MySQL JDBC Driver
Download `mysql-connector-java-8.x.x.jar` and add to classpath

### 4. Compile and Run

**Compile:**
```bash
javac -d bin src/util/*.java src/model/*.java src/dao/*.java src/gui/*.java src/app/*.java
```

**Run:**
```bash
java -cp bin app.MainApp
```

## Project Structure

```
src/
├── app/
│   └── MainApp.java              # Application entry point
├── gui/
│   ├── LoginFrame.java           # Login window
│   └── LibraryDashboard.java    # Main dashboard with tabs
├── model/
│   ├── Book.java                 # Book model
│   └── BorrowRecord.java        # Borrowing history record
├── dao/
│   └── BookDAO.java             # Database operations
└── util/
    └── DBConnection.java        # Database connection
```

## Login Credentials

- **Username**: admin
- **Password**: admin

## Skills Demonstrated

✅ Object-Oriented Programming (OOP)
✅ Classes and Objects
✅ Collections (ArrayList, List)
✅ JDBC with PreparedStatement
✅ Swing GUI Components
✅ Event Handling (Lambda expressions)
✅ Exception Handling
✅ Database Design and Operations
✅ Clean Code Practices

## Requirements

- Java 8 or higher
- MySQL 5.7 or higher
- MySQL JDBC Driver (mysql-connector-java-8.0.x)

