package com.faridnia.mealio.domain

import com.faridnia.mealio.domain.model.FoodLog
import com.faridnia.mealio.domain.util.CalorieCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [com.faridnia.mealio.domain.util.CalorieCalculator].
 * Verifies that calorie totals are correctly computed based on FoodLog.amount values.
 */
class CalorieCalculatorTest {

    @Test
    fun `calculate returns correct total`() {
        // Arrange: Create mock FoodLog entries with dummy epoch timestamps
        val logs = listOf(
            FoodLog(
                id = "1",
                epochDay = 20000L,
                foodTitle = "Apple",
                unit = "pcs",
                amount = 2,
                createdAt = 1730956800000L // Just an example timestamp
            ),
            FoodLog(
                id = "2",
                epochDay = 20000L,
                foodTitle = "Banana",
                unit = "pcs",
                amount = 3,
                createdAt = 1730957800000L
            )
        )

        // Act: Run the calorie calculation
        val total = CalorieCalculator.calculate(logs)

        // Assert: (2 + 3) * 10 = 50
        assertEquals(50, total)
    }

    @Test
    fun `calculate returns zero for empty list`() {
        // Arrange + Act + Assert
        assertEquals(0, CalorieCalculator.calculate(emptyList()))
    }

    @Test
    fun `calculate handles single item`() {
        val logs = listOf(
            FoodLog(
                id = "x",
                epochDay = 20001L,
                foodTitle = "Milk",
                unit = "ml",
                amount = 1,
                createdAt = 1730958900000L
            )
        )
        assertEquals(10, CalorieCalculator.calculate(logs))
    }
}
