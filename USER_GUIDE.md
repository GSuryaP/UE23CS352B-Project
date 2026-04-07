# Multi-User Expense Tracker - User Guide

## Overview
The Expense Tracker now supports multiple users with individual login accounts. Each user can:
- Create and manage their own expenses
- View only their personal expense data
- Access expense summaries and analytics

Additionally, admin users can:
- View all users
- Manage user roles (promote/demote users to admin)
- View expenses for any user
- Delete users (except themselves)

## Getting Started

### First Time Setup - Default Admin Account
When you first run the application, a default admin account is automatically created:

**Admin Credentials:**
- Username: `admin`
- Password: `admin123`

**⚠️ IMPORTANT:** Change this password after first login!

### Registration

1. Navigate to the login page: `http://localhost:8080/auth/login`
2. Click "Register here" link
3. Fill in the registration form:
   - Username (minimum 3 characters)
   - Email (valid email address)
   - Password (minimum 4 characters)
   - Confirm Password
4. Click "Register"
5. You'll be automatically logged in and redirected to the home page

### Login

1. Go to `http://localhost:8080/auth/login`
2. Enter your username and password
3. Click "Login"
4. You'll be redirected to the home page

## For Regular Users

### Adding an Expense

1. Click "Add Expense" in the navigation menu
2. Fill in the form:
   - **Date**: Date of the expense
   - **Category**: Type of expense (Food, Transport, Entertainment, etc.)
   - **Description**: Details about the expense
   - **Amount**: Cost in your currency (must be greater than 0)
3. Click "Add Expense"
4. You'll be redirected to the "View Expenses" page

### Viewing Your Expenses

1. Click "View Expenses" in the navigation menu
2. All your expenses are displayed in a table
3. You can:
   - Filter by category using the dropdown
   - Edit an expense by clicking the "Edit" button
   - Delete an expense by clicking the "Delete" button

### Editing an Expense

1. Go to "View Expenses"
2. Click the "Edit" button for an expense
3. Update the information
4. Click "Update"

### Deleting an Expense

1. Go to "View Expenses"
2. Click the "Delete" button next to an expense
3. The expense will be removed immediately

### Viewing Summary

1. Click "Summary" in the navigation menu
2. See:
   - Total amount spent across all your expenses
   - Breakdown by category (pie chart)
   - Category-wise spending distribution

### Logging Out

Click the "Logout" button in the top-right corner with your username displayed.

## For Admin Users

### Accessing Admin Dashboard

1. Log in with an admin account
2. You'll see an "Admin Panel" link in the navigation menu
3. Click it to access the admin dashboard

### Admin Dashboard

The admin dashboard shows:
- Total number of users in the system
- Quick link to manage users

### Managing Users

1. From the admin dashboard, click "Manage Users"
2. You'll see all non-admin users in a table
3. For each user, you can:

#### View User's Expenses
- Click "View Expenses" button
- See all expenses belonging to that user
- View their total spending

#### Promote a User to Admin
- Click "Promote" button
- User will now have admin privileges and access to the admin panel

#### Demote an Admin User
- Click "Demote" button
- User will return to regular user role

#### Delete a User
- Click "Delete" button
- The user account will be removed (this cannot be undone)
- **Note:** You cannot delete your own account

### Admin Restrictions

You cannot:
- Delete your own admin account
- Demote yourself from admin role using the admin panel

If you need to remove admin privileges from yourself, you would need another admin to do it.

## Common Tasks

### Change Your Password
Currently, passwords cannot be changed through the UI. To change your password:

1. **Using Database (MySQL):**
   ```sql
   UPDATE users SET password = HASHCODE('new_password_here') WHERE username = 'your_username';
   ```
   (Note: This uses the simple hash function implemented in the app)

2. **For Development:** You can directly update the password in the `users` table using a database tool.

**Recommendation:** Implement a "Change Password" feature in a future update.

### Create Multiple Expense Accounts

Each user account is separate. You can:
1. Create one user account per person
2. Each person logs in with their own credentials
3. Work independently without seeing others' expenses

## Data Privacy

- **Regular users** can only see their own expenses
- **Users cannot view** other users' expense data through the application
- **Admins can view** any user's expenses for management purposes
- **No data is shared** across user accounts unless explicitly shared by admin

## Troubleshooting

### "Username already exists" Error
- The username is taken by another user
- Try a different username

### "Email already exists" Error
- The email address is already registered
- Use a different email address

### "Passwords do not match" Error
- The password and confirm password fields don't match
- Re-enter both passwords carefully

### "Invalid username or password" Error
- Username doesn't exist OR password is incorrect
- Check your credentials and try again
- Make sure Caps Lock is not on

### Session Expired / Logged Out
- Sessions expire after 30 minutes of inactivity
- You'll need to log in again
- Your data is safe and saved in the database

### Can't See "Admin Panel" Link
- You don't have admin privileges
- Ask an admin user to promote your account
- Or log in with an admin account

## Security Tips

1. **Don't share your password** with anyone
2. **Change the default admin password** immediately after first login
3. **Use strong passwords** (mix of letters, numbers, and symbols recommended)
4. **Log out** when using shared computers
5. **Report suspicious activity** to your administrator

## Features Summary

| Feature | Regular User | Admin User |
|---------|:---:|:---:|
| Add/Edit/Delete own expenses | ✅ | ✅ |
| View own expense summary | ✅ | ✅ |
| View other users' expenses | ❌ | ✅ |
| Manage all users | ❌ | ✅ |
| Promote/Demote users | ❌ | ✅ |
| Delete users | ❌ | ✅ |
| Access admin panel | ❌ | ✅ |

## Support

For issues or questions:
1. Check this guide
2. Review the troubleshooting section
3. Check the application logs
4. Contact your system administrator (if applicable)
