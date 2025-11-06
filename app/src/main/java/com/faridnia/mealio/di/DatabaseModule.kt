package com.faridnia.mealio.di

import android.content.Context
import androidx.room.Room
import com.faridnia.mealio.data.local.AppDatabase
import com.faridnia.mealio.data.local.FoodLogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mealio-db"
        ).build()
    }

    @Provides
    fun provideFoodLogDao(appDatabase: AppDatabase): FoodLogDao {
        return appDatabase.foodLogDao()
    }
}