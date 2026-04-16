package com.programming3c.prog7313part2.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

/**
 * Author: Raghav Mahraj
 * Module: PROG7313 – POE Part 2
 *
 * Description:
 * This composable function represents the screen shown after a user has successfully
 * logged into the application. It acts as a simple confirmation screen and provides
 * a clear transition point for the next feature in the system.
 *
 * The screen displays a welcome message, the current user's username, and a logout
 * button which allows the user to end their session and return to the login screen.
 *
 * The layout follows a clean, card-based design using Jetpack Compose components,
 * ensuring consistency with the login screen and improving overall user experience
 * (Android Developers, n.d.; Nielsen, 1994).
 *
 * References:
 * Android Developers. (n.d.). Build a UI with Jetpack Compose.
 * Available at: https://developer.android.com/compose
 * [Accessed: 15 April 2026].
 *
 * Nielsen, J. (1994). 10 usability heuristics for user interface design.
 * Available at: https://www.nngroup.com/articles/ten-usability-heuristics/
 * [Accessed: 15 April 2026].
 */
@Composable
fun WelcomeScreen(
    username: String,
    onLogoutClick: () -> Unit
) {
    // The main column centres all content on screen, making the layout
    // easier to read and visually balanced.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // A card is used to group related UI elements and create a structured
        // and modern interface for the user.
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

                // This section displays a simple icon representing growth or progress,
                // which aligns with the budgeting theme of the application.
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    androidx.compose.foundation.layout.Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "↗",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Main welcome heading
                Text(
                    text = "Welcome to BudgetQuest",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.testTag("welcomeTitle") // Used for UI testing
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Displays the logged-in user's username
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("welcomeUsername") // Used for UI testing
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Supporting text to guide the user and indicate next steps
                Text(
                    text = "You're all set. Your budgeting journey starts here.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Logout button
                // Calls the provided logout function which clears the session
                // and returns the user to the login screen.
                Button(
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("signOutButton"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Sign Out")
                }
            }
        }
    }
}