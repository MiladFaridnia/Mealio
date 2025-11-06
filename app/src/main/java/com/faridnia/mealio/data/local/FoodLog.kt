package com.faridnia.mealio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Represents a single food log entry in the database.
 * Each instance of this class corresponds to a row in the "logs_food" table.
 */
@Entity(tableName = "logs_food")
data class FoodLog(
  @PrimaryKey val id: String = UUID.randomUUID().toString(),
  val epochDay: Long, // Day based on Asia/Tehran timezone
  val foodTitle: String,
  val unit: String,
  val amount: Int,
  val createdAt: Long // Epoch milliseconds for precise sorting
)
