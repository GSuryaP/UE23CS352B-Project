# Quick Start Guide - Multi-User Expense Tracker

## ⚡ 5-Minute Quick Start

### 1. Start the Application
```bash
cd Expense-Tracker
./mvnw spring-boot:run
```

Application will start at `http://localhost:8080`

### 2. Login as Admin
- **URL:** `http://localhost:8080/auth/login`
- **Username:** `admin`
- **Password:** `admin123`

### 3. Create a Test User
- Click "Logout" in top-right
- Click "Register here"
- Fill in registration form:
  - Username: `testuser`
  - Email: `test@example.com`
  - Password: `test1234`
  - Confirm: `test1234`
- Click "Register"

### 4. Add Your First Expense
- Click "Add Expense"
- Enter:
  - Date: `2024-01-15`
  - Category: `Food`
  - Description: `Lunch`
  - Amount: `250`
- Click "Add Expense"

### 5. View Your Expenses
- Click "View Expenses"
- See your expense in the table

### 6. Admin Features (Login as admin again)
- Click "Logout" → "Login" (admin/admin123)
- Click "Admin Panel" in navbar
- Click "Manage Users"
- See all users and their expenses
- Promote testuser to admin
- View testuser's expense details

---

## 📋 Default Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |

⚠️ **Change admin password after first use!**

---

## 🔗 Important URLs

| Feature | URL |
|---------|-----|
| Login | `http://localhost:8080/auth/login` |
| Register | `http://localhost:8080/auth/register` |
| Home | `http://localhost:8080/` |
| Add Expense | `http://localhost:8080/add` |
| View Expenses | `http://localhost:8080/view` |
| Summary | `http://localhost:8080/summary` |
| Admin Dashboard | `http://localhost:8080/admin/dashboard` |
| Manage Users | `http://localhost:8080/admin/users` |
| H2 Database | `http://localhost:8080/h2-console` |

---

## ✅ What's New

### ✨ Multi-User Features
- User registration and login
- Secure session management
- Each user sees only their expenses
- User data isolation

### 👨‍💼 Admin Features
- View all users
- View any user's expenses
- Promote/demote users to admin role
- Delete user accounts
- Admin dashboard

### 🛡️ Security
- Password hashing
- Session-based authentication
- Role-based access control
- Session timeout (30 minutes)

---

## 🧪 Test Scenarios

### Scenario 1: Multi-User Isolation
```
1. Create user1: add expense "Coffee - $50"
2. Create user2: try to view expenses → only your (empty) expenses shown
3. Login as admin: can see both users' expenses ✅
```

### Scenario 2: User Promotion
```
1. Login as admin
2. Promote user1 to admin
3. Logout and login as user1
4. See "Admin Panel" link in navbar ✅
```

### Scenario 3: Expense Management
```
1. Login as any user
2. Add expense
3. Edit it (change amount)
4. Delete it
5. Verify changes immediately ✅
```

---

## 📁 Project Structure Summary

```
New Files/Features:
├── User Authentication
│   ├── User.java (model)
│   ├── UserRepository.java
│   ├── UserService.java
│   └── AuthController.java
│
├── Admin Management
│   ├── AdminController.java
│   └── 3 admin templates
│
├── Session Management
│   └── SessionManager.java (session-scoped)
│
├── Updated Components
│   ├── Expense.java (added User foreign key)
│   ├── ExpenseService.java (user filtering)
│   ├── ExpenseController.java (auth checks)
│   └── ExpenseRepository.java (user queries)
│
├── Templates (New)
│   ├── login.html
│   ├── register.html
│   ├── admin-dashboard.html
│   ├── admin-users.html
│   └── admin-user-expenses.html
│
├── Documentation
│   ├── USER_GUIDE.md (comprehensive guide)
│   ├── MYSQL_MIGRATION.md (MySQL setup)
│   └── README.md (updated)
│
└── Configuration
    ├── DataInitializer.java (auto-create admin)
    ├── application.properties (updated)
    └── pom.xml (added dependencies)
```

---

## 🚀 Next Steps

### Option 1: Test Current Setup (H2 Database)
- The app works now with H2
- All data resets when app restarts
- Perfect for testing

### Option 2: Switch to MySQL (Persistent)
- Follow [MYSQL_MIGRATION.md](MYSQL_MIGRATION.md)
- Takes ~5 minutes
- Data persists across restarts

### Option 3: Deploy to Production
- Set strong admin password
- Configure MySQL database
- Use environment variables for credentials
- Enable HTTPS
- Set up proper password hashing (BCrypt)

---

## 🐛 Troubleshooting

| Problem | Solution |
|---------|----------|
| Login doesn't work | Use admin/admin123 or create new account |
| Can't see other users' expenses | You don't have admin role; ask admin to promote you |
| "Session expired" | Sessions timeout after 30 min; log in again |
| Port 8080 already in use | Change port in application.properties: `server.port=8081` |

---

## 📚 Learn More

- **Full User Guide:** [USER_GUIDE.md](USER_GUIDE.md)
- **MySQL Setup:** [MYSQL_MIGRATION.md](MYSQL_MIGRATION.md)
- **Original Project:** [Setup.md](Setup.md)
- **Updated README:** [README.md](README.md)

---

## ✅ Verification Checklist

After starting the app, verify these work:

- [ ] Can login as admin (admin/admin123)
- [ ] Can register new user
- [ ] Can add expense as new user
- [ ] Can view only own expenses
- [ ] Can logout
- [ ] Admin can see all users
- [ ] Admin can see any user's expenses
- [ ] Admin can promote user
- [ ] Admin can view admin panel
- [ ] Session expires after logout

---

**Happy expense tracking! 💰**
