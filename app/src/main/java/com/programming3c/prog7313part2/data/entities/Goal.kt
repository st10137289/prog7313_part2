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

@Entity(tableName= "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) // (Android Developers, n.d.)
    val goalId: Int=0,

    val userId: Int, //this links the goal to a user

    val month: Int,
    val year: Int,

    val minMonthGoal : Double,
    val maxMonthGoal : Double
)