package com.faridnia.mealio.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.mealio.domain.model.FoodLog
import com.faridnia.mealio.domain.repository.FoodLogRepository
import com.faridnia.mealio.domain.util.CalorieCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel responsible for managing today's food logs.
 *
 * Uses [FoodLogRepository] for data operations and exposes [kotlinx.coroutines.flow.StateFlow] to the UI layer.
 */
@HiltViewModel
class TodayViewModel @Inject constructor(
    private val foodLogRepository: FoodLogRepository
) : ViewModel() {

    // Backing property for today's food logs
    private val _todayLogs = MutableStateFlow<List<FoodLog>>(emptyList())
    val todayLogs: StateFlow<List<FoodLog>> = _todayLogs.asStateFlow()

    // Stream of total calories (auto-calculated from logs)
    val totalCalories: StateFlow<Int> = todayLogs
        .combine(todayLogs) { logs, _ -> CalorieCalculator.calculate(logs) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        loadTodayLogs()
    }

    /**
     * Loads logs for the current day (Asia/Tehran timezone).
     */
    private fun loadTodayLogs() {
        val today = LocalDate.now(ZoneId.of("Asia/Tehran"))
        foodLogRepository.getLogsForDay(today)
            .onEach { _todayLogs.value = it }
            .launchIn(viewModelScope)
    }

    /** Adds a new food log entry for today. */
    fun addLog(foodTitle: String, unit: String, amount: Double) {
        viewModelScope.launch {
            val today = LocalDate.now(ZoneId.of("Asia/Tehran"))
            val newLog = FoodLog(
                id = UUID.randomUUID().toString(),
                epochDay = today.toEpochDay(),
                foodTitle = foodTitle,
                unit = unit,
                amount = amount.toInt(),
                createdAt = System.currentTimeMillis()
            )
            foodLogRepository.addFoodLog(newLog)
        }
    }

    /** Updates an existing food log by ID. */
    fun updateLog(id: String, foodTitle: String, unit: String, amount: Double) {
        viewModelScope.launch {
            val today = LocalDate.now(ZoneId.of("Asia/Tehran"))
            val updated = FoodLog(
                id = id,
                epochDay = today.toEpochDay(),
                foodTitle = foodTitle,
                unit = unit,
                amount = amount.toInt(),
                createdAt = System.currentTimeMillis()
            )
            foodLogRepository.updateFoodLog(updated)
        }
    }

    /** Deletes a log and stores it temporarily for undo. */
    private var lastDeleted: FoodLog? = null

    fun deleteLog(log: FoodLog) {
        viewModelScope.launch {
            lastDeleted = log
            foodLogRepository.deleteFoodLog(log)
        }
    }

    /** Restores the last deleted item. */
    fun undoLastDelete() {
        viewModelScope.launch {
            lastDeleted?.let {
                foodLogRepository.addFoodLog(it)
                lastDeleted = null
            }
        }
    }
}
