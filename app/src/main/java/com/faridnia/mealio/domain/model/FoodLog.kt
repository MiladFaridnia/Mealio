package com.faridnia.mealio.domain.model

/**
 * Represents a single food log entry in the domain layer.
 */
data class FoodLog(
    val id: String,
    val epochDay: Long, // Day identifier (Tehran timezone)
    val foodTitle: String,
    val unit: String,
    val amount: Int,
    val createdAt: Long // Epoch millis for sorting
)
