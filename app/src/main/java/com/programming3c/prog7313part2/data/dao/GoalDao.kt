package com.programming3c.prog7313part2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.programming3c.prog7313part2.data.entities.Goal

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
 * Sql used for the queries
 * Oracle (n.d.) SQL Language Reference. Available at: https://docs.oracle.com/en/database/oracle/oracle-database/19/sqlrf/
 * [Accessed 13 April 2026].
 */

@Dao
interface GoalDao {

    @Insert // (Android Developers ,n.d.)
    suspend fun insertGoal(goal: Goal): Long

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("""
        SELECT * FROM goals
        WHERE userId = :userId
        AND month = :month
        AND year = :year
        LIMIT 1
    """)// (Oracle ,n.d.)
    suspend fun getGoalForMonth(
        userId: Int,
        month: Int,
        year: Int
    ): Goal?

    @Query("""
        SELECT * FROM goals 
        WHERE userId = :userId 
        ORDER BY year DESC, month DESC 
    """) // (Oracle ,n.d.)
    suspend fun getGoalsByUser(userId: Int): List<Goal>
}