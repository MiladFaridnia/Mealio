package com.faridnia.mealio.domain.repository

import com.faridnia.mealio.domain.model.FoodLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Defines operations for managing food logs.
 */
interface FoodLogRepository {

    /**
     * Returns all logs for a specific date.
     */
    fun getLogsForDay(date: LocalDate): Flow<List<FoodLog>>

    /**
     * Inserts a new log.
     */
    suspend fun addFoodLog(log: FoodLog)

    /**
     * Updates an existing log.
     */
    suspend fun updateFoodLog(log: FoodLog)

    /**
     * Deletes a log.
     */
    suspend fun deleteFoodLog(log: FoodLog)

    /**
     * Restores the last deleted log, if any.
     */
    suspend fun restoreLastDeleted()
}
