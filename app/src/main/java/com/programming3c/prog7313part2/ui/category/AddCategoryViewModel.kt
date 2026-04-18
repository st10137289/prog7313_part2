package com.programming3c.prog7313part2.ui.category

// Author: ST10089492 Ophec Funis
// Section: Expense Entry

// References:
// Android Developers. (n.d.). ViewModel overview.// Available at: https://developer.android.com/topic/libraries/architecture/viewmodel[Accessed: 16 April 2026].

// Android Developers. (n.d.). Accessing data using Room.Available at: https://developer.android.com/training/data-storage/room/accessing-data[Accessed: 16 April 2026].

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.programming3c.prog7313part2.data.AppDatabase
import com.programming3c.prog7313part2.data.entities.Category
import kotlinx.coroutines.launch

class AddCategoryViewModel(app: Application) : AndroidViewModel(app) {

    private val categoryDao = AppDatabase.getDatabase(app).categoryDao()

    var name by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var isSaved by mutableStateOf(false)

    fun save(userId: Int) {
        if (name.isBlank()) {
            errorMessage = "Category name can't be empty"
            return
        }

        viewModelScope.launch {
            // block duplicates so the category list stays clean
            val existing = categoryDao.getCategoryByName(userId, name.trim())
            if (existing != null) {
                errorMessage = "You already have a category called '${name.trim()}'"
                return@launch
            }

            categoryDao.insertCategory(Category(userId = userId, name = name.trim()))
            isSaved = true
        }
    }

    fun clearError() {
        errorMessage = null
    }

    // called on every screen entry to wipe any state left over from the previous visit
    fun resetAll() {
        name = ""
        errorMessage = null
        isSaved = false
    }
}
