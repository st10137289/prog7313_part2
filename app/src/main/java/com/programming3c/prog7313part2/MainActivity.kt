package com.programming3c.prog7313part2

import com.programming3c.prog7313part2.auth.AuthValidator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.programming3c.prog7313part2.auth.LoginScreen
import com.programming3c.prog7313part2.auth.RegisterScreen
import com.programming3c.prog7313part2.auth.SessionManager
import com.programming3c.prog7313part2.data.AppDatabase
import com.programming3c.prog7313part2.data.entities.User
import com.programming3c.prog7313part2.ui.goals.SetGoalScreen
import com.programming3c.prog7313part2.ui.home.HomeScreen
import com.programming3c.prog7313part2.ui.category.AddCategoryScreen
import com.programming3c.prog7313part2.ui.expense.AddExpenseScreen
import com.programming3c.prog7313part2.ui.report.CategoryTotalsScreen
import com.programming3c.prog7313part2.ui.report.ExpenseListScreen
import com.programming3c.prog7313part2.ui.theme.Prog7313part2Theme
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    ADD_EXPENSE,
    ADD_CATEGORY,
    SET_GOAL,
    VIEW_EXPENSES,
    CATEGORY_TOTALS
}

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase
    private lateinit var sessionManager: SessionManager

    private var isLoggedIn by mutableStateOf(false)
    private var loggedInUsername by mutableStateOf("")
    private var loginError by mutableStateOf<String?>(null)
    private var registerError by mutableStateOf<String?>(null)
    private var showRegister by mutableStateOf(false)
    private var currentScreen by mutableStateOf(Screen.HOME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)
        sessionManager = SessionManager(this)

        isLoggedIn = sessionManager.isLoggedIn()
        loggedInUsername = sessionManager.getUsername()

        enableEdgeToEdge()
        setContent {
            Prog7313part2Theme {
                if (isLoggedIn) {
                    when (currentScreen) {
                        Screen.HOME -> HomeScreen(
                            username = loggedInUsername,
                            onAddExpenseClick = { currentScreen = Screen.ADD_EXPENSE },
                            onAddCategoryClick = { currentScreen = Screen.ADD_CATEGORY },
                            onSetGoalClick = { currentScreen = Screen.SET_GOAL },
                            onViewExpensesClick = { currentScreen = Screen.VIEW_EXPENSES },
                            onCategoryTotalsClick = { currentScreen = Screen.CATEGORY_TOTALS },
                            onLogoutClick = {
                                sessionManager.clearSession()
                                isLoggedIn = false
                                loggedInUsername = ""
                                loginError = null
                                currentScreen = Screen.HOME
                            }
                        )
                        Screen.ADD_EXPENSE -> AddExpenseScreen(
                            userId = sessionManager.getUserId(),
                            onBack = { currentScreen = Screen.HOME }
                        )
                        Screen.ADD_CATEGORY -> AddCategoryScreen(
                            userId = sessionManager.getUserId(),
                            onBack = { currentScreen = Screen.HOME }
                        )
                        Screen.SET_GOAL -> SetGoalScreen(
                            userId = sessionManager.getUserId(),
                            onBack = { currentScreen = Screen.HOME }
                        )
                        Screen.VIEW_EXPENSES -> ExpenseListScreen(
                            userId = sessionManager.getUserId(),
                            onBack = { currentScreen = Screen.HOME }
                        )
                        Screen.CATEGORY_TOTALS -> CategoryTotalsScreen(
                            userId = sessionManager.getUserId(),
                            onBack = { currentScreen = Screen.HOME }
                        )
                    }
                } else if (showRegister) {
                    RegisterScreen(
                        errorMessage = registerError,
                        onRegisterClick = { username, password, confirmPassword ->
                            handleRegister(username, password, confirmPassword)
                        },
                        onBackClick = {
                            showRegister = false
                            registerError = null
                        }
                    )
                } else {
                    LoginScreen(
                        errorMessage = loginError,
                        onLoginClick = { username, password ->
                            handleLogin(username, password)
                        },
                        onRegisterClick = {
                            loginError = null
                            showRegister = true
                        }
                    )
                }
            }
        }
    }

    private fun handleLogin(username: String, password: String) {
        val validationError = AuthValidator.validateLoginInput(username, password)
        if (validationError != null) {
            loginError = validationError
            return
        }

        lifecycleScope.launch {
            val user = database.userDao().login(username, password)

            if (user != null) {
                sessionManager.saveLoginSession(user.userId, user.username)
                loggedInUsername = user.username
                isLoggedIn = true
                loginError = null
            } else {
                loginError = "Invalid username or password"
            }
        }
    }

    private fun handleRegister(username: String, password: String, confirmPassword: String) {
        when {
            username.isBlank() -> { registerError = "Enter a username"; return }
            password.isBlank() -> { registerError = "Enter a password"; return }
            password.length < 4 -> { registerError = "Password must be at least 4 characters"; return }
            password != confirmPassword -> { registerError = "Passwords do not match"; return }
        }

        lifecycleScope.launch {
            val existing = database.userDao().getUserByUsername(username)
            if (existing != null) {
                registerError = "Username already taken"
                return@launch
            }

            val newUserId = database.userDao().insertUser(
                User(username = username, password = password)
            )
            sessionManager.saveLoginSession(newUserId.toInt(), username)
            loggedInUsername = username
            isLoggedIn = true
            showRegister = false
            registerError = null
        }
    }
}
