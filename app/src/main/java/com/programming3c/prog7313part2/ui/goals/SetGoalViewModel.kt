package com.programming3c.prog7313part2.ui.goals

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.programming3c.prog7313part2.data.AppDatabase
import com.programming3c.prog7313part2.data.entities.Goal
import kotlinx.coroutines.launch
import java.util.Calendar

class SetGoalViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getDatabase(app)
    private val goalDao = db.goalDao()
    private val expenseDao = db.expenseDao()

    var minGoal by mutableStateOf("")
    var maxGoal by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)
    var spendingStatus by mutableStateOf("No status yet")
    var currentTotal by mutableStateOf(0.0)

    private var existingGoal: Goal? = null

    fun loadGoalAndStatus(userId: Int) {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        val startDate = "%04d-%02d-01".format(year, month)
        val endDate = "%04d-%02d-31".format(year, month)

        viewModelScope.launch {
            existingGoal = goalDao.getGoalForMonth(userId, month, year)

            existingGoal?.let {
                minGoal = it.minMonthGoal.toString()
                maxGoal = it.maxMonthGoal.toString()
            }

            currentTotal = expenseDao.getTotalSpentByPeriod(userId, startDate, endDate) ?: 0.0
            updateSpendingStatus()
        }
    }

    fun saveGoal(userId: Int) {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        val min = minGoal.trim().toDoubleOrNull()
        val max = maxGoal.trim().toDoubleOrNull()

        when {
            min == null || min < 0 -> {
                errorMessage = "Enter a valid minimum"
                successMessage = null
                return
            }
            max == null || max <= 0 -> {
                errorMessage = "Enter a valid maximum"
                successMessage = null
                return
            }
            max <= min -> {
                errorMessage = "Maximum must be greater than minimum"
                successMessage = null
                return
            }
        }

        viewModelScope.launch {
            val goal = Goal(
                goalId = existingGoal?.goalId ?: 0,
                userId = userId,
                month = month,
                year = year,
                minMonthGoal = min,
                maxMonthGoal = max
            )

            if (existingGoal == null) {
                goalDao.insertGoal(goal)
            } else {
                goalDao.updateGoal(goal)
            }

            existingGoal = goal
            errorMessage = null
            successMessage = "Goal saved successfully"
            updateSpendingStatus()
        }
    }

    private fun updateSpendingStatus() {
        val min = minGoal.trim().toDoubleOrNull()
        val max = maxGoal.trim().toDoubleOrNull()

        spendingStatus = when {
            min == null || max == null -> "Set your monthly goals to view status"
            currentTotal < min -> "Below goal"
            currentTotal > max -> "Overspending"
            else -> "On track"
        }
    }
}