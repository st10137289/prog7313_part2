package com.programming3c.prog7313part2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.programming3c.prog7313part2.data.entities.Expense

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
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("""
        SELECT * FROM expenses 
        WHERE userId = :userId 
        ORDER BY date DESC""") // (Oracle ,n.d.)
    suspend fun getAllExpensesByUser(userId: Int): List<Expense>

    @Query(""" 
        SELECT * FROM expenses
        WHERE userId = :userId
        AND date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)// (Oracle ,n.d.)
    suspend fun getExpensesByPeriod(
        userId: Int,
        startDate: String,
        endDate: String
    ): List<Expense>

    @Query("""
        SELECT SUM(amount) FROM expenses
        WHERE userId = :userId
        AND categoryId = :categoryId
        AND date BETWEEN :startDate AND :endDate
    """) // (Oracle ,n.d.)
    suspend fun getTotalForCategoryByPeriod(
        userId: Int,
        categoryId: Int,
        startDate: String,
        endDate: String
    ): Double?

    @Query("""
        SELECT SUM(amount) FROM expenses
        WHERE userId = :userId
        AND date BETWEEN :startDate AND :endDate
    """) // (Oracle ,n.d.)
    suspend fun getTotalSpentByPeriod(
        userId: Int,
        startDate: String,
        endDate: String
    ): Double?
}