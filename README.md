# Personal Expense Tracker System

A full-stack web application built using **Spring Boot, Thymeleaf, and MySQL Database** to manage and track personal expenses with multi-user support and admin functionality.

---

## Project Overview

The Personal Expense Tracker System allows:

**Regular Users:**
- Register and login with secure authentication
- Add daily expenses
- View only their own expenses
- Edit and delete their entries
- View personal spending summary
- Download personal expenses by category or by Month
- Filter and search their expenses dynamically

**Admin Users:**
- View and manage all users in the system
- Promote/demote users to/from admin role
- View any user's expenses
- Delete user accounts (except their own)

This project follows proper **MVC architecture** and demonstrates core Object-Oriented Analysis and Design (OOAD) principles with **multi-user support** and **role-based access control**.

---

## Tech Stack

| Layer        | Technology Used |
|-------------|----------------|
| Backend     | Spring Boot 4.0.2 |
| Frontend    | Thymeleaf, HTML5, CSS3 |
| Database    | MySQL |
| ORM         | Spring Data JPA (Hibernate) |
| Authentication | Custom Session Management |
| Security    | Spring Security |
| Build Tool  | Maven |
| Validation  | Jakarta Bean Validation |
| Version Control | Git & GitHub |

---

## Architecture

The project follows the **MVC (Model-View-Controller)** pattern with service layer separation:

**Models:**
- `Expense.java` - Expense entity
- `User.java` - User entity with roles

**Repositories (DAO Layer):**
- `ExpenseRepository` - Data access for expenses
- `UserRepository` - Data access for users

**Services:**
- `ExpenseService` - Business logic for expenses (user-specific filtering)
- `ReportService` - Business logic for generating and downloading reports
- `UserService` - Business logic for user management and authentication

**Controllers:**
- `ExpenseController` - Routes for expense CRUD operations
- `AuthController` - Routes for login, registration, logout
- `AdminController` - Routes for admin user management

**Utilities:**
- `SessionManager` - Session-scoped user management

**Views:** Thymeleaf HTML Templates for all pages

---

## Features Implemented

### Core Features
- ✅ Add/Edit/Delete Expenses (user-specific)
- ✅ View Personal Expenses
- ✅ User Registration & Login
- ✅ Session Management
- ✅ Logout functionality

### Multi-User Features
- ✅ Secure login system
- ✅ User registration
- ✅ Session-based authentication
- ✅ User-specific expense isolation
- ✅ Password hashing

### Admin Features
- ✅ View all users (non-admin)
- ✅ Promote/demote users to admin
- ✅ View any user's expenses
- ✅ Delete user accounts
- ✅ Admin dashboard

### Advanced User Features
- ✅ Expense Summary (Total Calculation)
- ✅ Expense Summary for other users (admin only)
- ✅ Download Expenditure report (Monthly or Category-wise Report)
- ✅ Category-based filtering
- ✅ Live search functionality
- ✅ Validation using annotations
- ✅ Responsive UI design

---

## New: Multi-User & Authentication System

### Default Admin Account
Application creates a default admin account on first run:
- **Username:** `admin`
- **Password:** `admin123`

⚠️ **Change this password immediately after first login!**

### User Registration
New users can register at `/auth/register` with:
- Unique username (minimum 3 characters)
- Valid email address
- Password (minimum 4 characters)

### Role-Based Access Control
- **USER Role:** Access own expenses only
- **ADMIN Role:** Access admin panel and all user management features

### Session Management
- Session timeout: 30 minutes of inactivity
- Automatic logout on browser close
- Session stored in user session scope

---

## Setup & Installation

### Clone Repository
```bash
git clone https://github.com/GSuryaP/Expense-Tracker.git
cd Expense-Tracker
```

### Make a MySQL user
Open MySQL:
```bash
sudo mysql -u root -p
```

Run the following commands in the MySQL console
```bash
CREATE DATABASE expense_tracker_db;
CREATE USER 'expense_user'@'localhost' IDENTIFIED BY 'password@123';
GRANT ALL PRIVILEGES ON expense_tracker_db.* TO 'expense_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```


### Run Application
```bash
mvn clean
mvn spring-boot:run
```

### Access Application
- **Home Page:** `http://localhost:8080/`
- **Login Page:** `http://localhost:8080/auth/login`
- **Registration:** `http://localhost:8080/auth/register`
- **Admin Panel:** `http://localhost:8080/admin/dashboard` (admin users only)

### First Steps After Running
1. Application starts with default admin account created
2. Access login page at `http://localhost:8080/auth/login`
3. Login as `admin` / `admin123`
4. **Change admin password** (database edit required currently)
5. Create regular user account for testing
6. Switch between accounts to verify multi-user isolation

---

## Database Configuration

### Current: MySQL
Data persists in MySQL running locally

---

## File Structure

```
Expense-Tracker/
├── src/main/java/com/pesu/expensetracker/
│   ├── controller/
│   │   ├── ExpenseController.java
│   │   ├── AuthController.java
│   │   └── AdminController.java
│   ├── model/
│   │   ├── Expense.java
│   │   └── User.java
│   ├── repository/
│   │   ├── ExpenseRepository.java
│   │   └── UserRepository.java
│   ├── service/
│   │   ├── ExpenseService.java
|   |   ├── ReportService.java
│   │   └── UserService.java
│   ├── util/
│   │   └── SessionManager.java
│   ├── config/
│   │   └── DataInitializer.java
│   └── ExpenseTrackerApplication.java
├── src/main/resources/
│   ├── templates/
│   │   ├── home.html
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── add-expense.html
│   │   ├── edit-expenses.html
│   │   ├── view-expenses.html
│   │   ├── summary.html
│   │   ├── admin-dashboard.html
│   │   ├── admin-users.html
│   │   └── admin-user-expenses.html
│   ├── static/css/
│   │   └── style.css
│   └── application.properties
├── pom.xml
├── README.md
├── Setup.md
```
---

## Authors
- [G S S Surya Prakash](https://github.com/GSuryaP), [Chandan Chatragadda](https://github.com/chandan365c) and [Drishti Golchha](https://github.com/golchha27)
