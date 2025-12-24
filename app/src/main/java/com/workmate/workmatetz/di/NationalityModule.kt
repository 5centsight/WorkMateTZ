package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.repository.NationalitiesRepositoryImpl
import com.workmate.workmatetz.domain.repository.NationalitiesRepository
import com.workmate.workmatetz.domain.usecases.GetNationalitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NationalityModule {
    @Provides
    @Singleton
    fun provideNationalitiesRepository(): NationalitiesRepository = NationalitiesRepositoryImpl()


    @Provides
    @Singleton
    fun provideGetNationalitiesUseCase(repository: NationalitiesRepository): GetNationalitiesUseCase =
        GetNationalitiesUseCase(repository)
}