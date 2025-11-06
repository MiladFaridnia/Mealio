package com.faridnia.mealio.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the FoodLog entity.
 * This interface defines the database operations for food logs.
 */
@Dao
interface FoodLogDao {
  /**
   * Retrieves all food logs for a specific day, ordered by their creation time.
   * Returns a Flow, so the UI can automatically update when the data changes.
   */
  @Query("SELECT * FROM logs_food WHERE epochDay = :epochDay ORDER BY createdAt ASC")
  fun getLogsForDay(epochDay: Long): Flow<List<FoodLog>>

  /**
   * Inserts a new food log into the database.
   * The 'suspend' keyword makes it a coroutine-friendly, non-blocking operation.
   */
  @Insert
  suspend fun insert(log: FoodLog)

  /**
   * Updates an existing food log.
   */
  @Update
  suspend fun update(log: FoodLog)

  /**
   * Deletes a food log from the database.
   */
  @Delete
  suspend fun delete(log: FoodLog)
}
