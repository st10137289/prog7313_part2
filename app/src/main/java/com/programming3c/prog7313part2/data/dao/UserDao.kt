package com.programming3c.prog7313part2.data.dao

/**
 * Author: st10137289 (David Porter)
 * Module: PROG7313 – Part 2
 *
 * Description:
 * This class forms part of the Room database implementation used for local data storage
 * in the budgeting application. It defines the structure and data access operations
 * for the database layer.
 *
 * References:
 * Android Developers. (n.d.). Accessing data using Room.
 * Available at: https://developer.android.com/training/data-storage/room/accessing-data
 * [Accessed: 13 April 2026].
 *
 * Kotlin (n.d.) Coroutines basics. Available at: https://kotlinlang.org/docs/coroutines-basics.html
 * [Accessed 13 April 2026].
 */

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.programming3c.prog7313part2.data.entities.User

@Dao // (Android Developers, n.d.)
interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long //(Kotlin, n.d.)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}