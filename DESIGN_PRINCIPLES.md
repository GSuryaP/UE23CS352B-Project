# GRASP and SOLID Principles Analysis

## Expense Tracker Project Architecture Analysis

---

## GRASP PRINCIPLES (General Responsibility Assignment Software Patterns)

### 1. **Single Responsibility Principle (Information Expert)**
**Definition:** Assign a responsibility to the class that has the information needed to fulfill it.

**Application in the Project:**

- **ExpenseService** - Expert on expense operations
  - Located at: [src/main/java/com/pesu/expensetracker/service/ExpenseService.java](src/main/java/com/pesu/expensetracker/service/ExpenseService.java)
  - Responsibilities: Add, delete, update, retrieve expenses
  - Has the information to manage expense lifecycle through ExpenseRepository
  
  ```java
  public void addExpense(Expense expense) {
      User currentUser = sessionManager.getLoggedInUser();
      if (currentUser != null) {
          expense.setUser(currentUser);
          expenseRepository.save(expense);
      }
  }
  ```

- **ReportService** - Expert on report generation
  - Located at: [src/main/java/com/pesu/expensetracker/service/ReportService.java](src/main/java/com/pesu/expensetracker/service/ReportService.java)
  - Responsibilities: Generate monthly reports, category-wise reports
  - Has the expertise to transform expense data into PDF reports
  
- **SessionManager** - Expert on user session management
  - Located at: [src/main/java/com/pesu/expensetracker/util/SessionManager.java](src/main/java/com/pesu/expensetracker/util/SessionManager.java)
  - Responsibilities: Track logged-in users, authenticate users

---

### 2. **Creator**
**Definition:** Assign object creation responsibility to a class that has the information and relationships needed.

**Application in the Project:**

- **ExpenseService** creates Expense objects with proper user assignment
  ```java
  public void addExpense(Expense expense) {
      User currentUser = sessionManager.getLoggedInUser();
      expense.setUser(currentUser);  // Creator knows the user context
      expenseRepository.save(expense);
  }
  ```

- **ReportService** creates PDF reports encapsulating all complexity
  - Responsible for creating byte arrays containing PDF documents
  - Knows how to construct monthly and category-wise report structures

- **Spring Dependency Injection** automatically creates instances of services and repositories
  ```java
  @Autowired
  private ExpenseService expenseService;
  ```

---

### 3. **Controller**
**Definition:** Assign responsibility for receiving or handling system events to an object.

**Application in the Project:**

- **ExpenseController** - Central controller for expense operations
  - Located at: [src/main/java/com/pesu/expensetracker/controller/ExpenseController.java](src/main/java/com/pesu/expensetracker/controller/ExpenseController.java)
  - Handles HTTP requests and delegates to services
  ```java
  @PostMapping("/add")
  public String saveExpense(@Valid @ModelAttribute Expense expense, BindingResult result) {
      if (result.hasErrors()) return "add-expense";
      expenseService.addExpense(expense);  // Delegate to service
      return "redirect:/view";
  }
  ```

- **AuthController** - Handles authentication events
  - Located at: [src/main/java/com/pesu/expensetracker/controller/AuthController.java](src/main/java/com/pesu/expensetracker/controller/AuthController.java)

- **AdminController** - Handles admin operations
  - Located at: [src/main/java/com/pesu/expensetracker/controller/AdminController.java](src/main/java/com/pesu/expensetracker/controller/AdminController.java)

---

### 4. **Low Coupling**
**Definition:** Assign responsibility to minimize dependencies between classes.

**Application in the Project:**

- **Layered Architecture** reduces coupling:
  - Controllers depend on Services (not directly on Repository)
  - Services depend on Repositories (data access layer)
  - Model classes have minimal dependencies
  
  ```
  Controller → Service → Repository → Database
  ```

- **Dependency Injection** promotes loose coupling:
  ```java
  @Autowired
  private ExpenseService expenseService;  // Injected, not created directly
  ```

- **Session Manager abstraction** decouples authentication logic
  ```java
  private boolean checkAuth(Model model) {
      if (!sessionManager.isLoggedIn()) {
          return false;
      }
      return true;
  }
  ```

---

### 5. **High Cohesion**
**Definition:** Assign responsibility so that the class has a strong, single focus.

**Application in the Project:**

- **ExpenseService** - Highly cohesive
  - All methods are related to expense management
  - Methods: addExpense, deleteExpense, getAllExpenses, getTotalExpenses, updateExpense

- **ReportService** - Highly cohesive
  - All methods focused on report generation
  - Methods: generateMonthlyReport, generateCategoryReport, createHeaderCell

- **Model classes** - Highly cohesive
  - **Expense** class only manages expense data with getters/setters
  - **User** class only manages user data
  - Located at: [src/main/java/com/pesu/expensetracker/model/](src/main/java/com/pesu/expensetracker/model/)

---

### 6. **Polymorphism**
**Definition:** Use object-oriented polymorphism to handle alternatives.

**Application in the Project:**

- **Report Generation Strategy**:
  - ReportService implements two report generation methods:
    - `generateMonthlyReport()` - Groups expenses by month
    - `generateCategoryReport()` - Groups expenses by category
  
  ```java
  public byte[] generateMonthlyReport(List<Expense> expenses, String userName) {...}
  public byte[] generateCategoryReport(List<Expense> expenses, String userName) {...}
  ```

- The controller uses polymorphic dispatch:
  ```java
  @GetMapping("/report/monthly")
  public ResponseEntity<byte[]> exportMonthlyReport() {...}
  
  @GetMapping("/report/category")
  public ResponseEntity<byte[]> exportCategoryReport() {...}
  ```

---

### 7. **Indirection**
**Definition:** Assign responsibility to an intermediate object to decouple objects.

**Application in the Project:**

- **SessionManager** acts as indirection layer for authentication
  - Controllers don't directly access session; they use SessionManager
  
  ```java
  if (!sessionManager.isLoggedIn()) {
      return "redirect:/auth/login";
  }
  ```

- **ExpenseService** acts as indirection between Controller and Repository
  ```java
  @GetMapping("/view")
  public String viewExpenses(Model model) {
      List<Expense> expenses = expenseService.getAllExpenses();  // Via service, not direct repo access
      model.addAttribute("expenses", expenses);
      return "view-expenses";
  }
  ```

- **ReportService** acts as indirection for PDF generation complexity
  - Controllers don't handle PDF creation directly; they delegate to ReportService

---

### 8. **Protected Variations**
**Definition:** Assign responsibility to protect objects from variations.

**Application in the Project:**

- **Authorization checks** protect against unauthorized access
  ```java
  private boolean checkAuth(Model model) {
      if (!sessionManager.isLoggedIn()) {
          return false;
      }
      return true;
  }
  ```

- **User isolation** protects user data
  ```java
  if (currentUser.isAdmin()) {
      return expenseRepository.findAll();  // Admin sees all
  }
  return expenseRepository.findByUser(currentUser);  // Regular user sees own expenses
  ```

- **Null safety** protects against missing values
  ```java
  Expense expense = expenseRepository.findById(id).orElse(null);
  if (expense != null) {...}
  ```

---

## SOLID PRINCIPLES

### 1. **S - Single Responsibility Principle (SRP)**
**Definition:** A class should have only one reason to change.

**Application in the Project:**

- **ExpenseService** - Single responsibility: manage expense operations
  - Changes only if expense business logic changes
  - Located at: [src/main/java/com/pesu/expensetracker/service/ExpenseService.java](src/main/java/com/pesu/expensetracker/service/ExpenseService.java)

- **ReportService** - Single responsibility: generate reports
  - Changes only if report generation logic/format changes
  - Located at: [src/main/java/com/pesu/expensetracker/service/ReportService.java](src/main/java/com/pesu/expensetracker/service/ReportService.java)

- **UserService** - Single responsibility: manage user operations
  - Located at: [src/main/java/com/pesu/expensetracker/service/UserService.java](src/main/java/com/pesu/expensetracker/service/UserService.java)

- **SessionManager** - Single responsibility: manage user sessions
  - Located at: [src/main/java/com/pesu/expensetracker/util/SessionManager.java](src/main/java/com/pesu/expensetracker/util/SessionManager.java)

---

### 2. **O - Open/Closed Principle (OCP)**
**Definition:** Software entities should be open for extension but closed for modification.

**Application in the Project:**

- **Report Generation** - Easily extensible
  
  Current implementation:
  ```java
  @GetMapping("/report/monthly")
  public ResponseEntity<byte[]> exportMonthlyReport() {...}
  
  @GetMapping("/report/category")
  public ResponseEntity<byte[]> exportCategoryReport() {...}
  ```
  
  **Extension Example:** To add a new report type (e.g., weekly, yearly):
  - Add new method in ReportService: `generateWeeklyReport()`
  - Add new endpoint in ExpenseController: `exportWeeklyReport()`
  - **No modification** to existing monthly/category methods needed

- **Service layer design** allows for extension
  - Can add new services (e.g., BudgetService, NotificationService) without modifying existing services

---

### 3. **L - Liskov Substitution Principle (LSP)**
**Definition:** Derived classes must be usable through base class references without breaking functionality.

**Application in the Project:**

- **Spring Framework Integration**
  
  All service classes follow Spring conventions:
  ```java
  @Service
  public class ExpenseService {...}
  
  @Service
  public class ReportService {...}
  
  @Service
  public class UserService {...}
  ```
  
  They can be injected and used interchangeably via Spring's dependency injection:
  ```java
  @Autowired
  private ExpenseService expenseService;  // Can be used without knowing implementation details
  ```

- **Repository Pattern**
  - ExpenseRepository and UserRepository both extend Spring Data JPA Repository
  - Can be used polymorphically:
  ```java
  @Repository
  public interface ExpenseRepository extends JpaRepository<Expense, Long> {...}
  ```

---

### 4. **I - Interface Segregation Principle (ISP)**
**Definition:** Clients should not depend on interfaces they don't use.

**Application in the Project:**

- **Focused Repository Interfaces**
  
  ```java
  // ExpenseRepository - only expense-related methods
  public interface ExpenseRepository extends JpaRepository<Expense, Long> {
      List<Expense> findByUser(User user);
  }
  
  // UserRepository - only user-related methods
  public interface UserRepository extends JpaRepository<User, Long> {
      User findByUsername(String username);
  }
  ```

- **Controller methods** expose only relevant functionality
  ```java
  @GetMapping("/add")     // Only what users need
  @GetMapping("/view")    // Only what users need
  @GetMapping("/summary") // Only what users need
  ```

---

### 5. **D - Dependency Inversion Principle (DIP)**
**Definition:** Depend on abstractions, not concrete implementations.

**Application in the Project:**

- **Spring Dependency Injection** - Controllers depend on service abstractions
  
  ```java
  @Controller
  public class ExpenseController {
      @Autowired
      private ExpenseService expenseService;  // Depends on abstraction (service interface)
      
      @PostMapping("/add")
      public String saveExpense(@Valid @ModelAttribute Expense expense, BindingResult result) {
          expenseService.addExpense(expense);  // Uses service, not concrete implementation
      }
  }
  ```

- **Repository Layer** - Services depend on repository abstraction
  
  ```java
  @Service
  public class ExpenseService {
      @Autowired
      private ExpenseRepository expenseRepository;  // Depends on interface/abstraction
  }
  ```

- **SessionManager** abstraction
  ```java
  @Autowired
  private SessionManager sessionManager;  // Depends on abstraction
  ```

---

## Design Patterns Applied

### 1. **Strategy Pattern** (in ReportService)
Different report generation strategies:
- `generateMonthlyReport()` - Monthly strategy
- `generateCategoryReport()` - Category-wise strategy

```java
// At runtime, the strategy is selected based on user choice
if (selectedReportType === 'monthly') {
    byte[] reportPdf = reportService.generateMonthlyReport(expenses, userName);
} else {
    byte[] reportPdf = reportService.generateCategoryReport(expenses, userName);
}
```

### 2. **Adapter Pattern** (in ReportService)
ReportService adapts expense data into PDF format:
```java
public byte[] generateMonthlyReport(List<Expense> expenses, String userName) {
    // Adapts List<Expense> → PDF ByteArray
}
```

### 3. **MVC Pattern**
Clear separation of concerns:
- **Model**: Expense, User
- **View**: HTML templates (summary.html, view-expenses.html, etc.)
- **Controller**: ExpenseController, AuthController, AdminController

### 4. **Repository Pattern**
Data access abstraction:
```java
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
```

### 5. **Facade Pattern** (in ExpenseController & ReportService)
Simplified interface to complex subsystems:
- ExpenseController provides simple endpoints
- ReportService hides complexity of PDF generation

---

## Architecture Summary

```
┌─────────────────────────────────────────────┐
│           User Interface (HTML/JS)           │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│      Controllers (Single Responsibility)    │
│   • ExpenseController                       │
│   • AuthController                          │
│   • AdminController                         │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│    Services (Business Logic)                │
│   • ExpenseService (High Cohesion)          │
│   • ReportService (Low Coupling)            │
│   • UserService (SRP)                       │
│   • SessionManager (Indirection)            │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│   Repositories (Data Access - DIP)          │
│   • ExpenseRepository                       │
│   • UserRepository                          │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│     Database (MySQL)                        │
└─────────────────────────────────────────────┘
```

---

## Key Findings

✅ **Strengths:**
1. Clear separation of concerns (Controller → Service → Repository)
2. Low coupling through dependency injection
3. High cohesion with focused classes
4. Extensible architecture (easy to add new report types)
5. Proper authorization and security checks
6. Information Expert pattern correctly applied
7. Layered architecture promotes SRP

⚠️ **Areas for Potential Improvement:**
1. Could extract a Report interface for better OCP compliance
2. Could implement more granular interfaces for ISP
3. Could add logging service (separate concern)
4. Could use enums for report types instead of strings

---

## How to Use the Export Feature

1. Navigate to `/summary` page
2. Select report type:
   - **Monthly Report**: Expenses grouped by month
   - **Category-wise Report**: Expenses grouped by category
3. Click "Export as PDF" button
4. PDF file will be downloaded automatically
5. Reports include:
   - User name and generation date
   - Detailed expense breakdown
   - Total amounts by period/category
   - Grand total at the end
