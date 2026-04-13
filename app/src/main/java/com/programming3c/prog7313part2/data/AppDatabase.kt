package com.programming3c.prog7313part2.data

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
 *
 * Android Developers b (n.d.) Room with a View – Step 7. Available at: https://developer.android.com/codelabs/android-room-with-a-view#7
 * [Accessed 13 April 2026].
*/


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.programming3c.prog7313part2.data.dao.UserDao
import com.programming3c.prog7313part2.data.dao.CategoryDao
import com.programming3c.prog7313part2.data.dao.ExpenseDao
import com.programming3c.prog7313part2.data.dao.GoalDao
import com.programming3c.prog7313part2.data.entities.User
import com.programming3c.prog7313part2.data.entities.Category
import com.programming3c.prog7313part2.data.entities.Expense
import com.programming3c.prog7313part2.data.entities.Goal

@Database(
    entities = [User::class, Category::class, Expense::class, Goal::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() { // (Android Developers ,n.d.)

    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun goalDao(): GoalDao

    //The database is implemented using a singleton pattern
    //to make sure there is  a single instance of the database that is used throughout
    //the application (Android Developers b, n.d.)/
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budget_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}