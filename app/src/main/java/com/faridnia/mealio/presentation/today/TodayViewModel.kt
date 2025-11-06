package com.faridnia.mealio.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.mealio.domain.model.FoodLog
import com.faridnia.mealio.domain.repository.FoodLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel responsible for managing today's food logs.
 *
 * Uses [com.faridnia.mealio.domain.repository.FoodLogRepository] for data operations and exposes [kotlinx.coroutines.flow.StateFlow] to the UI layer.
 */
@HiltViewModel
class TodayViewModel @Inject constructor(
    private val foodLogRepository: FoodLogRepository
) : ViewModel() {

    // Backing property for today's food logs
    private val _todayLogs = MutableStateFlow<List<FoodLog>>(emptyList())
    val todayLogs: StateFlow<List<FoodLog>> = _todayLogs.asStateFlow()

    init {
        loadTodayLogs()
    }

    /**
     * Loads logs for the current day (Asia/Tehran timezone).
     */
    private fun loadTodayLogs() {
        val today = LocalDate.now(ZoneId.of("Asia/Tehran"))
        foodLogRepository.getLogsForDay(today)
            .onEach { logs -> _todayLogs.value = logs }
            .launchIn(viewModelScope)
    }

    /**
     * Adds a new [FoodLog] entry for today.
     */
    fun addLog(foodTitle: String, unit: String, amount: Int) {
        viewModelScope.launch {
            val today = LocalDate.now(ZoneId.of("Asia/Tehran"))
            val newLog = FoodLog(
                id = UUID.randomUUID().toString(),
                epochDay = today.toEpochDay(),
                foodTitle = foodTitle,
                unit = unit,
                amount = amount,
                createdAt = System.currentTimeMillis()
            )
            foodLogRepository.addFoodLog(newLog)
        }
    }

    /**
     * Deletes a [FoodLog].
     */
    fun deleteLog(log: FoodLog) {
        viewModelScope.launch {
            foodLogRepository.deleteFoodLog(log)
        }
    }

    /**
     * Updates an existing [FoodLog].
     */
    fun updateLog(log: FoodLog) {
        viewModelScope.launch {
            foodLogRepository.updateFoodLog(log)
        }
    }

    /**
     * Restores the last deleted [FoodLog], if available.
     */
    fun undoLastDelete() {
        viewModelScope.launch {
            foodLogRepository.restoreLastDeleted()
        }
    }
}