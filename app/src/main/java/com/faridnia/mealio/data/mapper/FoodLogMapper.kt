package com.faridnia.mealio.data.mapper

import com.faridnia.mealio.data.local.FoodLogEntity
import com.faridnia.mealio.domain.model.FoodLog

/**
 * Converts between [FoodLogEntity] and [FoodLog].
 */
object FoodLogMapper {

    fun toEntity(domain: FoodLog): FoodLogEntity = FoodLogEntity(
        id = domain.id,
        epochDay = domain.epochDay,
        foodTitle = domain.foodTitle,
        unit = domain.unit,
        amount = domain.amount,
        createdAt = domain.createdAt
    )

    fun fromEntity(entity: FoodLogEntity): FoodLog = FoodLog(
        id = entity.id,
        epochDay = entity.epochDay,
        foodTitle = entity.foodTitle,
        unit = entity.unit,
        amount = entity.amount,
        createdAt = entity.createdAt
    )
}
