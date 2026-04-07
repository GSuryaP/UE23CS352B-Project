# Personal Expense Tracker System

A full-stack web application built using **Spring Boot, Thymeleaf, and H2 Database** (with MySQL migration support) to manage and track personal expenses with multi-user support and admin functionality.

---

## Project Overview

The Personal Expense Tracker System allows:

**Regular Users:**
- Register and login with secure authentication
- Add daily expenses
- View only their own expenses
- Edit and delete their entries
- View personal spending summary
- Visualize personal expenses by category using charts
- Filter and search their expenses dynamically

**Admin Users:**
- Access all features available to regular users
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
| Database    | H2 (In-Memory) or MySQL |
| ORM         | Spring Data JPA (Hibernate) |
| Authentication | Custom Session Management |
| Security    | Spring Security |
| Build Tool  | Maven |
| Validation  | Jakarta Bean Validation |
| Version Control | Git & GitHub |
| Charts      | Chart.js |

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
- ✅ Password hashing (basic implementation)

### Admin Features
- ✅ View all users (non-admin)
- ✅ Promote/demote users to admin
- ✅ View any user's expenses
- ✅ Delete user accounts
- ✅ Admin dashboard

### Advanced User Features
- ✅ Expense Summary (Total Calculation)
- ✅ Expense Summary for other users (admin only)
- ✅ Category-based filtering
- ✅ Live search functionality
- ✅ Pie/Bar Chart visualization
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

### Access H2 Database Console (Optional)
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:expensedb`
- **Username:** `sa`
- **Password:** (leave empty)

### First Steps After Running
1. Application starts with default admin account created
2. Access login page at `http://localhost:8080/auth/login`
3. Login as `admin` / `admin123`
4. **Change admin password** (database edit required currently)
5. Create regular user account for testing
6. Switch between accounts to verify multi-user isolation

---

## Database Configuration

### Current: H2 (In-Memory Database)
Data persists during application runtime but is reset on restart.

### Switch to MySQL (Persistent Database)
See [MYSQL_MIGRATION.md](MYSQL_MIGRATION.md) for detailed instructions on:
- Adding MySQL dependency
- Updating connection properties
- Creating MySQL database
- Verifying migration

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
├── USER_GUIDE.md
├── MYSQL_MIGRATION.md
└── Setup.md
```

---

## Documentation

- **[USER_GUIDE.md](USER_GUIDE.md)** - Comprehensive user guide for all features
- **[MYSQL_MIGRATION.md](MYSQL_MIGRATION.md)** - Instructions to migrate from H2 to MySQL
- **[Setup.md](Setup.md)** - Original setup instructions

---

## Testing the Multi-User System

### Test Case 1: User Isolation
1. Register as `user1`
2. Add expense: "Lunch - $10"
3. Logout
4. Register as `user2`
5. View Expenses - You should NOT see user1's expense
6. ✅ Verify: Only user2's expenses visible

### Test Case 2: Admin Access
1. Login as `admin`
2. Go to Admin Panel
3. View users and click "View Expenses" for user1
4. ✅ Verify: Can see user1's expenses

### Test Case 3: Role Management
1. Login as `admin`
2. Promote `user1` to admin
3. Logout and login as `user1`
4. ✅ Verify: "Admin Panel" link appears in navigation

---

## Future Enhancements

- [ ] Change password functionality
- [ ] Password reset via email
- [ ] Two-factor authentication
- [ ] Expense sharing between users
- [ ] Budget tracking
- [ ] Export expenses to CSV/PDF
- [ ] Mobile app
- [ ] Social expense splitting
- [ ] Recurring expenses
- [ ] Better password hashing (BCrypt)

---

## Known Limitations

- Simple password hashing (use bcrypt in production)
- Session stored in memory (use Redis for production clustering)
- No email verification during registration
- No password recovery mechanism
- Admin can delete themselves from database directly

---

## Authors
- Original: [G S S Surya Prakash](https://github.com/GSuryaP)
- Enhanced with: Multi-user authentication, Admin features, MySQL migration guide
