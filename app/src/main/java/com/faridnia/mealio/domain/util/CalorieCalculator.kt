package com.faridnia.mealio.domain.util

import com.faridnia.mealio.domain.model.FoodLog

/**
 * Utility object to calculate total calories based on food logs.
 * Currently uses a simplified formula: calories = amount × 10.
 * Later you can replace it with real nutritional data.
 */
object CalorieCalculator {

    /**
     * Calculates the total calories from a list of FoodLog items.
     *
     * @param logs List of FoodLog entries for the current day.
     * @return Total calories as Int.
     */
    fun calculate(logs: List<FoodLog>): Int {
        return logs.sumOf { (it.amount * 10.0) }.toInt()
    }
}
