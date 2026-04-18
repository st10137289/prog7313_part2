package com.programming3c.prog7313part2.ui.expense

// Author: ST10089492 Ophec Funis
// Section: Expense Entry

// References:
// Android Developers. (n.d.). ViewModel overview.Available at: https://developer.android.com/topic/libraries/architecture/viewmodel[Accessed: 16 April 2026].

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.programming3c.prog7313part2.data.AppDatabase
import com.programming3c.prog7313part2.data.entities.Category
import com.programming3c.prog7313part2.data.entities.Expense
import kotlinx.coroutines.launch

class AddExpenseViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getDatabase(app)
    private val expenseDao = db.expenseDao()
    private val categoryDao = db.categoryDao()

    var amount by mutableStateOf("")
    var date by mutableStateOf("")
    var startTime by mutableStateOf("")
    var endTime by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedCategoryId by mutableStateOf(-1)
    var selectedCategoryName by mutableStateOf("Select a category")
    var categories by mutableStateOf<List<Category>>(emptyList())
    var errorMessage by mutableStateOf<String?>(null)
    var isSaved by mutableStateOf(false)

    fun loadCategories(userId: Int) {
        viewModelScope.launch {
            categories = categoryDao.getCategoriesByUser(userId)
        }
    }

    fun selectCategory(category: Category) {
        selectedCategoryId = category.categoryId
        selectedCategoryName = category.name
        errorMessage = null
    }

    fun save(userId: Int) {
        // validate every field before touching the db
        val amountVal = amount.trim().toDoubleOrNull()

        when {
            amountVal == null || amountVal <= 0 -> {
                errorMessage = "Enter a valid amount greater than 0"
                return
            }
            date.isBlank() -> {
                errorMessage = "Pick a date"
                return
            }
            startTime.isBlank() -> {
                errorMessage = "Pick a start time"
                return
            }
            endTime.isBlank() -> {
                errorMessage = "Pick an end time"
                return
            }
            // HH:mm strings compare correctly as plain strings when zero-padded
            endTime <= startTime -> {
                errorMessage = "End time must be after start time"
                return
            }
            description.isBlank() -> {
                errorMessage = "Add a description"
                return
            }
            selectedCategoryId == -1 -> {
                errorMessage = "Select a category"
                return
            }
        }

        viewModelScope.launch {
            expenseDao.insertExpense(
                Expense(
                    userId = userId,
                    categoryId = selectedCategoryId,
                    amount = amountVal!!,
                    description = description.trim(),
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    imageUrl = null  // Not sure who yet but Image uploader/media should handle photo upload
                )
            )
            isSaved = true
        }
    }

    fun clearError() {
        errorMessage = null
    }

    // called on every screen entry to wipe any state left over from the previous visit
    fun resetAll() {
        amount = ""
        date = ""
        startTime = ""
        endTime = ""
        description = ""
        selectedCategoryId = -1
        selectedCategoryName = "Select a category"
        errorMessage = null
        isSaved = false
    }
}
