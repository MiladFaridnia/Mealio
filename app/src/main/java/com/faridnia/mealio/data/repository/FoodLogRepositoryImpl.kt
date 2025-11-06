package com.faridnia.mealio.data.repository

import com.faridnia.mealio.data.local.FoodLog
import com.faridnia.mealio.data.local.FoodLogDao
import com.faridnia.mealio.domain.repository.FoodLogRepository
import kotlinx.coroutines.flow.Flow
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

    /**
     * Retrieves all food logs for a specific day.
     *
     * @param date The date to retrieve logs for.
     * @return A Flow of a list of FoodLog entities for the given day.
     */
    override fun getLogsForDay(date: LocalDate): Flow<List<FoodLog>> {
        return foodLogDao.getLogsForDay(date.toEpochDay())
    }

    /**
     * Adds a new food log to the database.
     *
     * @param log The FoodLog to add.
     */
    override suspend fun addFoodLog(log: FoodLog) {
        foodLogDao.insert(log)
    }

    /**
     * Updates an existing food log.
     *
     * @param log The FoodLog to update.
     */
    override suspend fun updateFoodLog(log: FoodLog) {
        foodLogDao.update(log)
    }

    /**
     * Deletes a food log and caches it for a potential undo operation.
     *
     * @param log The FoodLog to delete.
     */
    override suspend fun deleteFoodLog(log: FoodLog) {
        lastDeleted = log
        foodLogDao.delete(log)
    }

    /**
     * Restores the last deleted food log.
     */
    override suspend fun restoreLastDeleted() {
        lastDeleted?.let {
            addFoodLog(it)
            lastDeleted = null
        }
    }
}

/*
* How to use in a ViewModel:
*
* @HiltViewModel
* class MyViewModel @Inject constructor(
*     private val foodLogRepository: FoodLogRepository
* ) : ViewModel() {
*
*     val todayLogs = foodLogRepository.getLogsForDay(LocalDate.now())
*
*     fun onAddLog(log: FoodLog) {
*         viewModelScope.launch {
*             foodLogRepository.addFoodLog(log)
*         }
*     }
* }
*/
