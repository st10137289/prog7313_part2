package com.programming3c.prog7313part2.auth

import android.content.Context

/**
 * Author: Raghav Mahraj
 * Module: PROG7313 – POE Part 2
 *
 * Description:
 * This class is responsible for managing the user's login session within the application.
 * It uses SharedPreferences to store and retrieve basic user session data such as
 * login status, user ID, and username.
 *
 * SharedPreferences provides a lightweight and persistent storage mechanism that
 * allows data to be retained even after the application is closed and reopened
 * (Android Developers, n.d.).
 *
 * This ensures that users do not have to log in repeatedly unless they explicitly
 * choose to log out.
 *
 * References:
 * Android Developers. (n.d.). Save key-value data.
 * Available at: https://developer.android.com/training/data-storage/shared-preferences
 * [Accessed: 15 April 2026].
 */
class SessionManager(context: Context) {

    // SharedPreferences instance used to store session-related data
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    /**
     * Saves the user's login session.
     *
     * @param userId The unique ID of the logged-in user
     * @param username The username of the logged-in user
     *
     * This function stores key session data so that the app can remember
     * the user between sessions without requiring them to log in again.
     */
    fun saveLoginSession(userId: Int, username: String) {
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putInt("user_id", userId)
            .putString("username", username)
            .apply()
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @return true if the user is logged in, false otherwise
     *
     * This value is used to determine which screen should be shown
     * when the application starts (login screen or main screen).
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    /**
     * Retrieves the stored user ID.
     *
     * @return the user ID if available, or -1 if no session exists
     */
    fun getUserId(): Int {
        return prefs.getInt("user_id", -1)
    }

    /**
     * Retrieves the stored username.
     *
     * @return the username if available, or an empty string if not found
     */
    fun getUsername(): String {
        return prefs.getString("username", "") ?: ""
    }

    /**
     * Clears the current session.
     *
     * This function removes all stored session data, effectively logging
     * the user out and returning the application to its initial state.
     */
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}