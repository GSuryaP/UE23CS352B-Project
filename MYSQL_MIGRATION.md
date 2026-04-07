# Multi-User Authentication & MySQL Migration Guide

## Current Status
✅ Multi-user authentication system implemented
✅ Admin user management implemented  
✅ Simple login/registration system implemented
❌ MySQL database not yet configured (Next step)

## Switching from H2 to MySQL

### Step 1: Add MySQL Dependency
Update `pom.xml` and replace the H2 dependency with MySQL:

```xml
<!-- Remove this: -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Add this instead: -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### Step 2: Update application.properties
Replace the H2 configuration with MySQL:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker_db
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Remove H2 configuration
# spring.h2.console.enabled=false
```

### Step 3: Create MySQL Database
Before running the application, create the database in MySQL:

```sql
CREATE DATABASE expense_tracker_db;
```

Or using MySQL Workbench:
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Right-click on "Databases"
4. Select "Create New Database"
5. Enter name: `expense_tracker_db`
6. Click "Apply"

### Step 4: Verify MySQL Server is Running
- **Windows**: Services > MySQL80 (or your version) should be running
- **Linux**: `sudo systemctl status mysql`
- **Mac**: System Preferences > MySQL > Check status

### Step 5: Rebuild and Test
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Step 6: Verify Database Connection
- Visit `http://localhost:8080/auth/login`
- Register a new user  
- Check that user is created in MySQL database with:

```sql
USE expense_tracker_db;
SELECT * FROM users;
```

## Troubleshooting

### "Connection refused" Error
- Ensure MySQL server is running
- Verify hostname and port (default: localhost:3306)
- Check username and password

### "Unknown database" Error
- MySQL database `expense_tracker_db` doesn't exist
- Create it using the SQL command above

### Port Already in Use
If you need to change MySQL port, update:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/expense_tracker_db
```

### Tables Not Creating
If tables don't auto-create, ensure:
```properties
spring.jpa.hibernate.ddl-auto=update
```

## Migration Checklist
- [ ] Add MySQL dependency to pom.xml
- [ ] Update application.properties with MySQL config
- [ ] Create `expense_tracker_db` database in MySQL
- [ ] Verify MySQL server is running
- [ ] Run `mvnw clean install`
- [ ] Test login at http://localhost:8080/auth/login
- [ ] Verify users table in MySQL database

## Reverting to H2
To switch back to H2 (in-memory database):
1. Undo the pom.xml changes (restore H2 dependency)
2. Undo the application.properties changes (restore H2 config)
3. Run `mvnw clean install`

Database will be reset each time the application starts (H2 is in-memory).
