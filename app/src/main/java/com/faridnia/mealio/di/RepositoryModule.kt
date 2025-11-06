package com.faridnia.mealio.di

import com.faridnia.mealio.data.repository.FoodLogRepositoryImpl
import com.faridnia.mealio.domain.repository.FoodLogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodLogRepository(
        foodLogRepositoryImpl: FoodLogRepositoryImpl
    ): FoodLogRepository
}