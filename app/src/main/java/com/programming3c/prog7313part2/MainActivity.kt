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
import com.programming3c.prog7313part2.auth.SessionManager
import com.programming3c.prog7313part2.auth.WelcomeScreen
import com.programming3c.prog7313part2.data.AppDatabase
import com.programming3c.prog7313part2.data.entities.User
import com.programming3c.prog7313part2.ui.theme.Prog7313part2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase
    private lateinit var sessionManager: SessionManager

    private var isLoggedIn by mutableStateOf(false)
    private var loggedInUsername by mutableStateOf("")
    private var loginError by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)
        sessionManager = SessionManager(this)

        isLoggedIn = sessionManager.isLoggedIn()
        loggedInUsername = sessionManager.getUsername()

        lifecycleScope.launch {
            seedDemoUsers()
        }

        enableEdgeToEdge()
        setContent {
            Prog7313part2Theme {
                if (isLoggedIn) {
                    WelcomeScreen(
                        username = loggedInUsername,
                        onLogoutClick = {
                            sessionManager.clearSession()
                            isLoggedIn = false
                            loggedInUsername = ""
                            loginError = null
                        }
                    )
                } else {
                    LoginScreen(
                        errorMessage = loginError,
                        onLoginClick = { username, password ->
                            handleLogin(username, password)
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
                loginError = "Invalid username or password."
            }
        }
    }

    private suspend fun seedDemoUsers() {
        val demoUsers = listOf(
            User(username = "admin", password = "1234"),
            User(username = "user1", password = "budget1")
        )

        for (user in demoUsers) {
            val existingUser = database.userDao().getUserByUsername(user.username)
            if (existingUser == null) {
                database.userDao().insertUser(user)
            }
        }
    }
}