package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.datasource.LocalDataSource
import com.workmate.workmatetz.data.datasource.RemoteDataSource
import com.workmate.workmatetz.data.local.UserDao
import com.workmate.workmatetz.data.remote.RandomUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(api: RandomUserApi): RemoteDataSource =
        RemoteDataSource(api)

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: UserDao): LocalDataSource =
        LocalDataSource(dao)
}