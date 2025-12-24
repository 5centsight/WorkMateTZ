package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.datasource.LocalDataSource
import com.workmate.workmatetz.data.datasource.RemoteDataSource
import com.workmate.workmatetz.data.repository.RandomUserRepositoryImpl
import com.workmate.workmatetz.domain.repository.RandomUserRepository
import com.workmate.workmatetz.domain.usecases.DeleteUserUseCase
import com.workmate.workmatetz.domain.usecases.GetAllUsersUseCase
import com.workmate.workmatetz.domain.usecases.GetRandomUserUseCase
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserFeatureModule {

    @Provides
    @Singleton
    fun provideRandomUserRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): RandomUserRepository = RandomUserRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideGetRandomUserUseCase(
        repository: RandomUserRepository
    ): GetRandomUserUseCase = GetRandomUserUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserBySeedUseCase(
        repository: RandomUserRepository
    ): GetUserBySeedUseCase = GetUserBySeedUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllUsersUseCase(
        repository: RandomUserRepository
    ): GetAllUsersUseCase = GetAllUsersUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(
        repository: RandomUserRepository
    ): DeleteUserUseCase = DeleteUserUseCase(repository)
}