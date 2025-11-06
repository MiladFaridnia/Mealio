package com.faridnia.mealio.data.repository

import com.faridnia.mealio.data.local.FoodLogDao
import com.faridnia.mealio.domain.model.FoodLog
import com.faridnia.mealio.domain.repository.FoodLogRepository
import com.faridnia.mealio.data.mapper.FoodLogMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

/**
 * Repository for managing FoodLog data.
 *
 * @property foodLogDao The Data Access Object for FoodLog entities.
 */
class FoodLogRepositoryImpl @Inject constructor(
    private val foodLogDao: FoodLogDao
) : FoodLogRepository {

    private var lastDeleted: FoodLog? = null

    override fun getLogsForDay(date: LocalDate): Flow<List<FoodLog>> {
        val epochDay = date.toEpochDay()
        return foodLogDao.getLogsForDay(epochDay)
            .map { list -> list.map(FoodLogMapper::fromEntity) }
    }

    override suspend fun addFoodLog(log: FoodLog) {
        foodLogDao.insert(FoodLogMapper.toEntity(log))
    }

    override suspend fun updateFoodLog(log: FoodLog) {
        foodLogDao.update(FoodLogMapper.toEntity(log))
    }

    override suspend fun deleteFoodLog(log: FoodLog) {
        lastDeleted = log
        foodLogDao.delete(FoodLogMapper.toEntity(log))
    }

    override suspend fun restoreLastDeleted() {
        lastDeleted?.let {
            addFoodLog(it)
            lastDeleted = null
        }
    }
}
