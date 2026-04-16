package com.programming3c.prog7313part2.auth

/**
 * Author: Raghav Mahraj
 * Module: PROG7313 – POE Part 2
 *
 * Description:
 * This object is responsible for validating user input during the login process.
 * It checks whether the username and password fields are empty and returns
 * appropriate error messages to guide the user.
 *
 * The validation is kept separate from the UI logic to improve code organisation,
 * readability, and reusability across the application (Android Developers, n.d.).
 *
 * References:
 * Android Developers. (n.d.). Improve app architecture.
 * Available at: https://developer.android.com/topic/architecture
 * [Accessed: 15 April 2026].
 */

object AuthValidator {

    /**
     * Validates the login input fields.
     *
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return A String containing an error message if validation fails,
     *         or null if the input is valid
     *
     * The function uses conditional checks to ensure that required fields
     * are not left empty. This prevents invalid input from being processed
     * further in the application and improves overall user experience
     * (Nielsen, 1994).
     */
    fun validateLoginInput(username: String, password: String): String? {
        return when {

            // Both fields are empty
            username.isBlank() && password.isBlank() ->
                "Please enter your username and password."

            // Username is missing
            username.isBlank() ->
                "Please enter your username."

            // Password is missing
            password.isBlank() ->
                "Please enter your password."

            // Input is valid
            else -> null
        }
    }
}