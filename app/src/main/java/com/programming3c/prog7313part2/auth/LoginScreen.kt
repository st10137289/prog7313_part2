package com.programming3c.prog7313part2.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Author: Raghav Mahraj
 * Module: PROG7313 – POE Part 2
 *
 * Description:
 * This composable function displays the login screen for the BudgetQuest application.
 * It allows the user to enter a username and password, displays validation feedback,
 * and sends the input values to the login handler when the Sign In button is pressed.
 *
 * The screen was designed using Jetpack Compose components to create a clean,
 * modern and user-friendly interface. Layout structure, spacing and alignment were
 * used carefully to improve readability and usability (Android Developers, n.d.a;
 * Nielsen, 1994).
 *
 * References:
 * Android Developers. (n.d.a). Build a UI with Jetpack Compose.
 * Available at: https://developer.android.com/compose
 * [Accessed: 15 April 2026].
 *
 * Nielsen, J. (1994). 10 usability heuristics for user interface design.
 * Available at: https://www.nngroup.com/articles/ten-usability-heuristics/
 * [Accessed: 15 April 2026].
 */
@Composable
fun LoginScreen(
    errorMessage: String?,
    onLoginClick: (String, String) -> Unit
) {
    // These state variables store the current values typed by the user
    // into the username and password input fields while the screen is active
    // (Android Developers, n.d.a).
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // The main outer column centres the card on screen and keeps the
    // login form visually balanced and easy to follow.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // A card is used to group the login components together so that the
        // screen feels cleaner and more structured for the user.
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // This section acts as a simple brand header for the app.
                // The upward arrow represents progress and growth, which links
                // well with the budgeting theme of BudgetQuest.
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.foundation.layout.Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    ) {
                        androidx.compose.foundation.layout.Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(0.3f)
                                .padding(4.dp)
                        ) {
                            Text(
                                text = "↗",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "BudgetQuest",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Track smarter. Spend better.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Username input field.
                // testTag is included to make this field easier to target during
                // automated UI testing (Android Developers, n.d.b).
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("usernameField")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Password input field.
                // PasswordVisualTransformation hides the characters entered by
                // the user, which is standard practice for login interfaces
                // (Android Developers, n.d.c).
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("passwordField")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // If an error message is received, it is shown here to guide
                // the user and prevent confusion during login.
                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("errorMessage")
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Sign In button.
                // The current input values are trimmed before being passed to
                // the login function so that accidental spaces do not affect
                // authentication.
                Button(
                    onClick = {
                        onLoginClick(username.trim(), password.trim())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("signInButton"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Sign In")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // This text button acts as a small hint for the user since
                // demo accounts were seeded into the database for testing.
                TextButton(onClick = { }) {
                    Text("Use one of the demo accounts provided")
                }
            }
        }
    }
}