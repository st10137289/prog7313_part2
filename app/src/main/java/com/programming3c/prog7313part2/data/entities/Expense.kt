package com.programming3c.prog7313part2.data.entities

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
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) // (Android Developers, n.d.)
    val expenseId: Int=0,
    val userId: Int, //this links the expense to a user
    val categoryId : Int, //this links a category to a expense
    val amount: Double,
    val description: String,

    val date: String, //This keeps the date simple, but we could change it later
    val startTime: String,
    val endTime: String,

    val imageUrl: String? = null //When the user uploads a photo this is where it is stored
)