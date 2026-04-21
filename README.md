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

---

# PROG7313 POE – Part 2 (Expense Entry & Category UI)

---

## Author
Ophec Funis (ST10089492)

---

## Overview

This section covers the expense entry feature and category management UI for BudgetQuest.

The database layer was already set up by Member 2, so this part focuses on building the screens and view models that sit on top of it.

I also added the registration screen and cleaned up the login UI since those were needed to properly test the flow.

---

## Screens Built

### HomeScreen
The screen users land on after logging in.

Has three buttons:
- Add Expense: goes to the expense form
- Add Category: goes to the category form
- Sign Out: clears session and returns to login

---

### AddCategoryScreen

Simple form for creating a new spending category.

Fields:
- Category name (text input)

Validation:
- Blocks empty names
- Blocks duplicate category names (checks the database before saving)

On save:
- Inserts into `Category` table via `CategoryDao`
- Navigates back automatically

---

### AddExpenseScreen

The main feature of this section. A scrollable form that collects all the required expense info.

Fields:
- Amount (R): decimal keyboard, must be greater than 0
- Date: opens a `DatePickerDialog`, stores as YYYY-MM-DD
- Start time: opens a `TimePickerDialog`, stores as HH:mm
- End time: same as above
- Description: plain text
- Category: dropdown populated from the database for that user

Validation (6 checks):
1. Amount must be a valid number greater than 0
2. Date must be picked
3. Start time must be picked
4. End time must be picked
5. End time must be after start time
6. Description cannot be blank
7. A category must be selected

Errors show inline so the user knows exactly what to fix.

On save:
- Inserts into `Expense` table via `ExpenseDao`
- `imageUrl` is saved as null: photo upload is for another member to handle
- Navigates back after successful save

---

### RegisterScreen

Added because there was no way to create a new user without it.

Features:
- Username and password fields
- Checks username isn't already taken
- Requires password to be at least 4 characters
- Logs the user in automatically after registering
- Removed the hardcoded demo users from the auth flow

---

## ViewModels

### AddCategoryViewModel
- Extends `AndroidViewModel` so it can access the database
- Holds form state (name, errorMessage, isSaved)
- `save()` runs the duplicate check then inserts
- `resetAll()` clears everything when the screen opens: stops old data showing up on revisit

### AddExpenseViewModel
- Loads the category list from the database when the screen opens
- Holds state for all 6 form fields plus the selected category
- `save()` runs all validation then inserts the expense
- Same `resetAll()` pattern as above

---

## Theme

Applied the BudgetQuest colour scheme across all screens.

- Primary: green
- Background: white / light grey
- Configured in `Color.kt` and `Theme.kt`
- Dynamic colour is disabled so the theme stays consistent across devices

---

## Notes for Other Members

Member 4 (photo + goals):
- `imageUrl` field already exists on the `Expense` entity: just needs to be populated
- `Goal` entity and `GoalDao` are ready: `SetGoalScreen.kt` has the UI skeleton, just needs the save logic wired up

Member 5 (list + reports):
- `getExpensesByPeriod(userId, startDate, endDate)` is in `ExpenseDao`: ready to use
- `getTotalForCategoryByPeriod(...)` is also in `ExpenseDao`
- `ExpenseListScreen.kt` and `CategoryTotalsScreen.kt` have the screen skeletons waiting

---

## References

- Android Developers. (n.d.). Build a UI with Jetpack Compose.
Available at: https://developer.android.com/compose
[Accessed: 16 April 2026].

- Android Developers. (n.d.). DatePickerDialog.
Available at: https://developer.android.com/reference/android/app/DatePickerDialog
[Accessed: 16 April 2026].

- Android Developers. (n.d.). ViewModel overview.
Available at: https://developer.android.com/topic/libraries/architecture/viewmodel
[Accessed: 16 April 2026].

- Android Developers. (n.d.). Accessing data using Room.
Available at: https://developer.android.com/training/data-storage/room/accessing-data
[Accessed: 16 April 2026].

- Nielsen, J. (1994). 10 usability heuristics for user interface design.
Available at: https://www.nngroup.com/articles/ten-usability-heuristics/
[Accessed: 16 April 2026].

---
# PROG7313 POE – Part 2 (Media + Goals)

---

## Author  
Shahaan Pillay (ST10438099)

---

## Overview  

This section covers the media functionality (photo upload) and the monthly goal tracking system for BudgetQuest.
The database layer and expense entry system were already set up by previous members, so this part focuses on extending that functionality by adding image support and implementing goal-based spending logic.
The aim is to improve user interaction by allowing users to attach receipts to expenses and track their spending against monthly targets.

---

## Features Implemented  

### Media uploads

Users can attach a receipt image when creating an expense.

**Functionality:**
- Opens device gallery to select an image  
- Converts selected image to URI string  
- Stores URI in the `imageUrl` field  
- Saves image reference with the expense  

**On save:**
- Image is included when inserting into the `Expense` table via `ExpenseDao`  

---

### Monthly Goals  

Users can define spending targets for each month.

**Fields:**
- Minimum monthly goal  
- Maximum monthly goal  

**Validation:**
- Minimum must be ≥ 0  
- Maximum must be greater than 0  
- Maximum must be greater than minimum  

---
## Screens Built  

### SetGoalScreen  

Screen used to manage monthly goals.

**Features:**
- Displays current month and year  
- Loads existing goal if available  
- Allows creating or updating goals  
- Displays total spending for the month  
- Displays spending status

---

## Goal Logic  

The system compares total monthly spending against the user’s goals.

**Spending conditions:**
- Below goal → spending is less than minimum  
- On track → spending is between minimum and maximum  
- Overspending → spending exceeds maximum  

**How it works:**
- Uses `getTotalSpentByPeriod(userId, startDate, endDate)` from `ExpenseDao`  
- Retrieves goal using `getGoalForMonth(userId, month, year)` from `GoalDao`  
- Calculates and displays result in real time  

---


## Notes for Other Members  

- `imageUrl` is now populated and can be used in list/report screens  
- Goals are stored per user, month, and year  
- `getTotalSpentByPeriod(...)` is used for all calculations  

---

## Validation  

The following validation checks were implemented to ensure correct user input:

- Minimum goal must be greater than or equal to 0  
- Maximum goal must be greater than 0  
- Maximum goal must be greater than the minimum goal  
- Image selection is optional but handled safely 

Errors are displayed clearly to guide the user when invalid input is entered.

---

## Testing  

The following tests were performed to verify functionality:

### Media 
- Selected image from gallery successfully  
- Verified no crashes when selecting an image  
- Confirmed image URI is saved with the expense  
- Confirmed expense saves correctly with and without an image  

### Goals
- Tested empty input → validation errors displayed  
- Tested invalid ranges (min > max) → error shown  
- Tested valid input → goal saved successfully  
- Reopened screen → goal values persisted correctly  
- Verified total monthly spending is calculated  
- Verified status updates correctly (Below / On track / Overspending)  

### General
- Verified app runs without crashes  
- Verified navigation between screens works correctly

--- 

## Known Limitations  

- Images are stored as URI strings only  
- Monthly range uses fixed date format (01–31)  

---

## Final Notes  

This implementation provides:

- Media support for expenses  
- Full monthly goal tracking system  
- Integration between expenses and goals  
- Real-time spending feedback  

---

## References  

- Android Developers. (n.d.). Activity Result APIs.  
  Available at: https://developer.android.com/training/basics/intents/result  
  [Accessed: April 2026]

- Android Developers. (n.d.). ViewModel overview.  
  Available at: https://developer.android.com/topic/libraries/architecture/viewmodel  
  [Accessed: April 2026]

- Android Developers. (n.d.). Accessing data using Room.  
  Available at: https://developer.android.com/training/data-storage/room/accessing-data  
  [Accessed: April 2026]



# PROG7313 POE – Part 2 (List + Reports)

---

## Author
Rahil Ramdass (ST10434494)

---

## Overview

This section covers the **List and Reports** functionality for the BudgetQuest application.

The database queries and screen skeletons were already prepared by previous members, so this part focused on completing the user-facing reporting screens and connecting them to the existing Room database structure.

The aim of this section was to allow users to view stored expenses for a selected period and to summarise their spending by category.

---

## Features Implemented

### Expense List

Users can view all saved expenses for a selected date range.

**Functionality:**
- Allows the user to select a **from date** and **to date**
- Loads expenses using `ExpenseDao.getExpensesByPeriod(userId, startDate, endDate)`
- Displays stored expense entries in a scrollable list
- Shows the following details for each expense:
  - Amount
  - Date
  - Start time and end time
  - Description
  - Category name
- Indicates when a receipt image is attached

**How it works:**
- Dates are selected using `DatePickerDialog`
- The selected dates are stored in `YYYY-MM-DD` format
- Expenses are retrieved from the Room database for the logged-in user only
- Category names are loaded using `CategoryDao.getCategoriesByUser(userId)` so that category IDs can be displayed as readable names

---

### Category Totals Report

Users can view total spending per category for a selected date range.

**Functionality:**
- Allows the user to select a **from date** and **to date**
- Calculates the total spent in each category for that period
- Displays an **overall total**
- Displays category totals in a scrollable list
- Sorts totals from highest to lowest

**How it works:**
- Loads the user’s categories from `CategoryDao`
- Uses `ExpenseDao.getTotalForCategoryByPeriod(userId, categoryId, startDate, endDate)` to calculate totals per category
- Uses `ExpenseDao.getTotalSpentByPeriod(userId, startDate, endDate)` to calculate the overall total
- Only categories with spending in the selected period are shown

---

## Screens Completed

### ExpenseListScreen

This screen was completed to provide a full expense viewing feature.

**Features:**
- Date range filtering
- Automatic loading of expenses for the selected period
- Expense cards showing the stored details clearly
- Empty-state message when no expenses exist for the selected period
- Back button to return to the home screen

---

### CategoryTotalsScreen

This screen was completed to provide spending summaries by category.

**Features:**
- Date range filtering
- Overall total display
- Individual totals per category
- Empty-state message when no data exists for the selected period
- Back button to return to the home screen

---

## Integration

The List and Reports section was integrated into the existing application flow.

**Integration completed:**
- Connected `ExpenseListScreen` to the Home screen through the **View Expenses** button
- Connected `CategoryTotalsScreen` to the Home screen through the **Category Totals** button
- Used the active logged-in user session so that each user only sees their own expenses and totals
- Worked with the existing Room database and DAO layer without requiring database redesign

---

## Validation and Behaviour

The following checks and behaviours were implemented:

- Both dates must be selected before data is loaded
- The **from date** cannot be after the **to date**
- If there are no expenses for the selected range, a clear message is displayed
- If there is no spending data for the selected range, a clear message is displayed
- Totals are formatted clearly for readability

---

## Testing

The following tests were performed to verify the feature:

### Expense List
- Verified that saved expenses appear for the correct date range
- Verified that expenses do not appear outside the selected period
- Verified that category names display correctly
- Verified that receipt image presence is indicated correctly
- Verified that the empty-state message appears when no expenses are found

### Category Totals
- Verified that totals are calculated correctly per category
- Verified that the overall total matches the sum of category totals
- Verified that only categories with spending are shown
- Verified that the empty-state message appears when no spending exists for the selected period

### Navigation
- Verified that both report screens open correctly from the Home screen
- Verified that the Back button returns the user to the Home screen

---

## Final Notes

This implementation provides:

- A complete expense viewing screen
- Date-range filtering for stored expenses
- Category-based spending summaries
- Overall spending totals for selected periods
- Integration of reporting features into the existing application flow

---

## References

- Android Developers. (n.d.). Build a UI with Jetpack Compose.  
  Available at: https://developer.android.com/compose  
  [Accessed: 21 April 2026].

- Android Developers. (n.d.). Lists and grids in Compose.  
  Available at: https://developer.android.com/develop/ui/compose/lists  
  [Accessed: 21 April 2026].

- Android Developers. (n.d.). DatePickerDialog.  
  Available at: https://developer.android.com/reference/android/app/DatePickerDialog  
  [Accessed: 21 April 2026].

- Android Developers. (n.d.). Accessing data using Room.  
  Available at: https://developer.android.com/training/data-storage/room/accessing-data  
  [Accessed: 21 April 2026].

- Kotlin. (n.d.). Coroutines basics.  
  Available at: https://kotlinlang.org/docs/coroutines-basics.html  
  [Accessed: 21 April 2026].















