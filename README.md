# Personal Expense Tracker System

A full-stack web application built using **Spring Boot, Thymeleaf, and H2 Database** to manage and track personal expenses efficiently.

---

## Project Overview

The Personal Expense Tracker System allows users to:

- Add daily expenses
- View all recorded expenses
- Edit and delete entries
- View total spending summary
- Visualize expenses by category using charts
- Filter and search expenses dynamically

This project follows proper **MVC architecture** and demonstrates core Object-Oriented Analysis and Design (OOAD) principles.

---

## Tech Stack

| Layer        | Technology Used |
|-------------|----------------|
| Backend     | Spring Boot |
| Frontend    | Thymeleaf, HTML, CSS |
| Database    | H2 (In-Memory) |
| ORM         | Spring Data JPA (Hibernate) |
| Build Tool  | Maven |
| Validation  | Jakarta Bean Validation |
| Version Control | Git & GitHub |
| Charts      | Chart.js |

---

## Architecture

The project follows the **MVC (Model-View-Controller)** pattern:

- **Model** → `Expense.java`
- **Repository (DAO Layer)** → `ExpenseRepository`
- **Service Layer** → `ExpenseService`
- **Controller Layer** → `ExpenseController`
- **View Layer** → Thymeleaf HTML Templates

---

## Features Implemented

### Core Features
- Add Expense
- View Expenses
- Edit Expense
- Delete Expense

### Advanced Features
- Expense Summary (Total Calculation)
- Category-based filtering
- Live search functionality
- Bar Chart visualization of expenses
- Validation using annotations
- Responsive UI design

---
## Setup & Installation

### Clone Repository
```
git clone https://github.com/GSuryaP/Expense-Tracker.git
cd Expense-Tracker
```

### Run Application
```
mvn clean
mvn spring-boot:run
```

### Access Application
- Open browser:```http://localhost:8080```

### Access H2 Database Console (Optional)
- ```http://localhost:8080/h2-console```
- JDBC URL: ```jdbc:h2:mem:expensedb```
- Username: ```sa```
- Password: (leave empty)

---

## Author
- [G S S Surya Prakash](https://github.com/GSuryaP)
