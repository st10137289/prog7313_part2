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
