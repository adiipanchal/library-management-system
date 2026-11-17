# Library Management System

A Java console application backed by a MySQL database. It manages a book catalog and user accounts, with passwords stored as salted hashes. Built with plain JDBC and a layered structure (model, DAO, utility classes).

**Status:** in progress. Book management and user registration/login are finished and tested. Issuing and returning books is the next module.

## Tech stack

- Java 17
- MySQL 8
- JDBC (MySQL Connector/J)
- Eclipse, MySQL Workbench, Git

## Features

Built:

- Book management: add, view all (sorted by title), search by title, update, delete
- User registration and login with two roles, `ADMIN` and `MEMBER`
- Passwords hashed with PBKDF2 (HmacSHA256, random salt per user, 65,536 iterations)
- Duplicate emails rejected by a `UNIQUE` constraint
- Parameterized queries (`PreparedStatement`) to prevent SQL injection
- Database settings kept in `db.properties`, which is not committed to Git

Planned:

- Issue and return books with due dates
- Fine calculation for late returns
- Per-member borrowing limit
- Overdue and category reports using SQL joins and views

## Database design

Three tables in the `library_db` database:

| Table | Purpose | Key columns |
|---|---|---|
| `users` | Accounts | `user_id` (PK), `email` (UNIQUE), `password_hash`, `role` |
| `books` | Catalog | `book_id` (PK), `title`, `author`, `category`, `total_copies`, `available_copies` |
| `transactions` | Issue and return records | `txn_id` (PK), `user_id` (FK), `book_id` (FK), `issue_date`, `due_date`, `return_date`, `fine` |

`CHECK` constraints on `books` keep `available_copies` between 0 and `total_copies`. Foreign keys in `transactions` stop records from pointing to users or books that do not exist.

## Project structure

```
Library/
├── src/
│   ├── model/        Book.java, User.java
│   ├── dao/          BookDAO.java, UserDAO.java
│   ├── util/         DBConnection.java, PasswordUtil.java
│   ├── BookTest.java
│   └── UserTest.java
├── schema.sql
├── db.properties.example
└── .gitignore
```

- `model`: plain classes that represent one table row each
- `dao`: all SQL and JDBC code lives here
- `util`: database connection and password hashing

## How to run

1. Install Java 17 (or newer) and MySQL 8.
2. Run `schema.sql` in MySQL Workbench to create the database, tables, and sample books.
3. Download MySQL Connector/J and add the JAR to the project (in Eclipse: Build Path, Configure Build Path, Libraries, Add External JARs).
4. Copy `db.properties.example` to `db.properties` in the project root and enter your MySQL password:
   ```
   db.url=jdbc:mysql://localhost:3306/library_db
   db.user=root
   db.password=your_password
   ```
5. Run `util/DBConnection.java`. It should print `Connected to: library_db`.
6. Run `BookTest.java` to try the book operations, and `UserTest.java` to try registration and login.

The two sample users in `schema.sql` have placeholder hashes and cannot log in. Delete them (`DELETE FROM users WHERE password_hash LIKE 'temp_hash%';`) and register real users through `UserDAO`.

## Author

Aditya Panchal
