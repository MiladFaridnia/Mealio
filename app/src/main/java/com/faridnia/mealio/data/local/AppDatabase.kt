package com.faridnia.mealio.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The main database class for the application.
 * It lists the entities the database contains and provides access to the DAOs.
 */
@Database(entities = [FoodLogEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

  /**
   * Provides access to the FoodLogDao.
   */
  abstract fun foodLogDao(): FoodLogDao
}
