package com.faridnia.mealio.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for food logs.
 */
@Dao
interface FoodLogDao {
    /**
     * Retrieves all food logs for a specific day, ordered by their creation time.
     * Returns a Flow, so the UI can automatically update when the data changes.
     */
    @Query("SELECT * FROM logs_food WHERE epochDay = :epochDay ORDER BY createdAt DESC")
    fun getLogsForDay(epochDay: Long): Flow<List<FoodLogEntity>>

    /**
     * Inserts a new food log into the database.
     * The 'suspend' keyword makes it a coroutine-friendly, non-blocking operation.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: FoodLogEntity)

    /**
     * Updates an existing food log.
     */
    @Update
    suspend fun update(log: FoodLogEntity)

    /**
     * Deletes a food log from the database.
     */
    @Delete
    suspend fun delete(log: FoodLogEntity)
}
