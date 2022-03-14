package com.application.shoprye.dao

import androidx.room.*
import com.application.shoprye.models.RyeJobAndShoppingCartEntry
import com.application.shoprye.models.ShoppingCartEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM shopping_cart_entries")
    fun getShoppingCartEntries(): Flow<List<ShoppingCartEntry>>

    @Query("SELECT EXISTS(SELECT 1 FROM shopping_cart_entries WHERE rye_job_id = :ryeJobId LIMIT 1)")
    fun isInCart(ryeJobId: Int): Flow<Boolean>

    /**
     * This query will tell Room to query both the [RyeJob] and [ShoppingCartEntry] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM rye_jobs WHERE id IN (SELECT DISTINCT(rye_job_id) FROM shopping_cart_entries)")
    fun getRyeJobAndShoppingCartEntries(): Flow<List<RyeJobAndShoppingCartEntry>>

    @Insert
    suspend fun insertShoppingCartEntry(shoppingCartEntry: ShoppingCartEntry): Long

    @Delete
    suspend fun deleteShoppingCartEntry(shoppingCartEntry: ShoppingCartEntry)

    @Query("DELETE FROM shopping_cart_entries")
    suspend fun deleteAllShoppingCartEntries()
}