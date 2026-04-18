# PROG7313 POE – Part 2 (Database)

## Author
David Porter (ST10137289)

---

## Overview

This part of the project focuses on the **RoomDB database setup and core backend structure** for the budgeting application.

All core data models and database access layers have been implemented.

---

## Database Implementation

### AppDatabase
- Room database configured with:
  - User
  - Category
  - Expense
  - Goal

- Singleton pattern implemented using:
    AppDatabase.getDatabase(context)

- Database name: `budget_database`

---

## Entities Implemented

### 1. User
**Fields:**
- userId (Primary Key)
- username
- password

**Purpose:**
- Used for login functionality

---

### 2. Category
**Fields:**
- categoryId (Primary Key)
- userId (links category to user)
- name

**Purpose:**
- Each user has their own categories

---

### 3. Expense
**Fields:**
- expenseId (Primary Key)
- userId
- categoryId
- amount
- description
- date (String)
- startTime
- endTime
- imageUrl (optional)

**Supports:**
- Expense tracking
- Time tracking
- Receipt image storage

---

### 4. Goal
**Fields:**
- goalId (Primary Key)
- userId
- month
- year
- minMonthGoal
- maxMonthGoal

**Purpose:**
- Used for monthly budgeting goals

---

## DAO Layer (Data Access)

- All DAO functions are suspend functions  
➡️ Must be called using coroutines

---

### UserDao
- Insert user
- Delete user
- Get all users
- Login:

    login(username, password)

- Get user by username

---

### CategoryDao
- Insert / Update / Delete category
- Get categories by user
- Get category by ID
- Get category by name (useful for validation)

---

### ExpenseDao
- Insert / Update / Delete expense
- Get all expenses by user
- Get expenses by date range
- Get total spent per category (date range)
- Get total spent overall (date range)

**Important:**
- Date is stored as String → must stay consistent format (e.g. YYYY-MM-DD) This can be changed if needed

**Supports:**
- Filtering by period
- Category totals

---

### GoalDao
- Insert / Update / Delete goal
- Get goal for specific month + year
- Get all goals for user

---

## How to Use the Database

### Get instance:

    val db = AppDatabase.getDatabase(context)

### Access DAOs:

    val userDao = db.userDao()
    val categoryDao = db.categoryDao()
    val expenseDao = db.expenseDao()
    val goalDao = db.goalDao()

---

## CRITICAL NOTES (READ THIS)

### 1. Coroutines REQUIRED
- All DAO functions are suspend

 > Calling them on the main thread will crash the app. This can be changed if we think there is a better way

**Use:**
- CoroutineScope
- viewModelScope (recommended)

---

### 2. No UI Connected
- Backend only
- No Activities/Fragments connected
- No user flow implemented

---

### 3. No Validation Implemented
- Duplicate categories possible
- Invalid inputs not handled

---

## Known Limitations

- Date stored as String (not Date object)

---

## Project Structure

    data/
     ├── entities/
     │    ├── User.kt
     │    ├── Category.kt
     │    ├── Expense.kt
     │    ├── Goal.kt
     │
     ├── dao/
     │    ├── UserDao.kt
     │    ├── CategoryDao.kt
     │    ├── ExpenseDao.kt
     │    ├── GoalDao.kt
     │
     ├── AppDatabase.kt

---

## Final Notes

This implementation provides:
- Full RoomDB setup
- Complete data layer
- Advanced queries for filtering and totals


---

## Reference:
In the classes that used


---



# PROG7313 POE – Part 2 (Authentication)

---

## Author
Raghav Mahraj

---

## Overview

This part of the project focuses on implementing the **authentication system** for the BudgetQuest application.

The goal is to allow users to securely log into the app using a username and password, maintain their session, and provide a clean transition into the rest of the system.

The authentication system integrates with the existing **RoomDB database structure** and ensures that only valid users can access the application.

---

## Authentication Implementation

### Login System

The login system allows users to enter their credentials and validates them before granting access.

**Features implemented:**
- Username and password input fields
- Input validation (empty fields handling)
- Authentication using RoomDB (`UserDao`)
- Error message display for invalid input
- Navigation to the next screen after successful login

---

### Session Management

A session system was implemented using **SharedPreferences** to maintain login state.

**Stored data:**
- Login status
- User ID
- Username

**Functionality:**
- Keeps user logged in after app restart
- Determines initial screen on app launch
- Clears session on logout

---

### Demo Users

To simplify testing, users are automatically seeded into the database.

| Username | Password |
|----------|---------|
| admin    | 1234    |
| user1    | budget1 |

---

### Welcome Screen

After successful login, users are taken to a confirmation screen.

**Features:**
- Displays application name (**BudgetQuest**)
- Shows logged-in username
- Provides a logout button
- Acts as a transition point for further development

---

## User Interface Design

The UI was built using **Jetpack Compose** and follows a clean, card-based layout.

**Design considerations:**
- Centralised layout for readability
- Consistent spacing and alignment
- Clear input fields and buttons
- Simple branding using the **BudgetQuest** name and icon

Tagline used:
> Track smarter. Spend better.

---

## Testing

Basic unit testing was implemented for the authentication validation logic.

**Test cases**

- Empty username and password
- Empty username
- Empty password
- Valid input

All tests pass successfully.

---

## References

- Android Developers. (n.d.). Build a UI with Jetpack Compose.
Available at: https://developer.android.com/compose
[Accessed: 15 April 2026].

- Android Developers. (n.d.). Improve app architecture.
Available at: https://developer.android.com/topic/architecture
[Accessed: 15 April 2026].

- Android Developers. (n.d.). Save key-value data.
Available at: https://developer.android.com/training/data-storage/shared-preferences
[Accessed: 15 April 2026].

- Android Developers. (n.d.). Accessing data using Room.
Available at: https://developer.android.com/training/data-storage/room/accessing-data
[Accessed: 15 April 2026].

- Android Developers. (n.d.). Testing in Jetpack Compose.
Available at: https://developer.android.com/jetpack/compose/testing
[Accessed: 15 April 2026].

- Nielsen, J. (1994). 10 usability heuristics for user interface design.
Available at: https://www.nngroup.com/articles/ten-usability-heuristics/
[Accessed: 15 April 2026].






















