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
import androidx.room.Update
import com.programming3c.prog7313part2.data.entities.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category): Long // //(Kotlin, n.d.)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories WHERE userId = :userId ORDER BY name ASC")
    suspend fun getCategoriesByUser(userId: Int): List<Category>

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Int): Category?

    @Query("SELECT * FROM categories WHERE userId = :userId AND name = :name LIMIT 1")
    suspend fun getCategoryByName(userId: Int, name: String): Category?
}