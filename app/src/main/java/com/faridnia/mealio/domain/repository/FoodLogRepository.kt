package com.faridnia.mealio.domain.repository

import com.faridnia.mealio.data.local.FoodLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface FoodLogRepository {

    fun getLogsForDay(date: LocalDate): Flow<List<FoodLog>>

    suspend fun addFoodLog(log: FoodLog)

    suspend fun updateFoodLog(log: FoodLog)

    suspend fun deleteFoodLog(log: FoodLog)

    suspend fun restoreLastDeleted()
}
