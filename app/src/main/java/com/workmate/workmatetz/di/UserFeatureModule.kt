package com.workmate.workmatetz.di

import android.content.Context
import com.workmate.workmatetz.data.datasource.LocalDataSource
import com.workmate.workmatetz.data.datasource.RemoteDataSource
import com.workmate.workmatetz.data.repository.RandomUserRepositoryImpl
import com.workmate.workmatetz.domain.repository.RandomUserRepository
import com.workmate.workmatetz.domain.usecases.CopyUserToClipboardUseCase
import com.workmate.workmatetz.domain.usecases.DeleteUserUseCase
import com.workmate.workmatetz.domain.usecases.FormatUserInfoUseCase
import com.workmate.workmatetz.domain.usecases.GetAllUsersUseCase
import com.workmate.workmatetz.domain.usecases.GetRandomUserUseCase
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import com.workmate.workmatetz.domain.usecases.ShareUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideShareUserUseCase(
        @ApplicationContext context: Context,
        getUserBySeedUseCase: GetUserBySeedUseCase,
        formatUserInfoUseCase: FormatUserInfoUseCase
    ): ShareUserUseCase {
        return ShareUserUseCase(context, getUserBySeedUseCase, formatUserInfoUseCase)
    }

    @Provides
    @Singleton
    fun provideCopyUserToClipboardUseCase(
        @ApplicationContext context: Context,
        getUserBySeedUseCase: GetUserBySeedUseCase,
        formatUserInfoUseCase: FormatUserInfoUseCase
    ): CopyUserToClipboardUseCase {
        return CopyUserToClipboardUseCase(context, getUserBySeedUseCase, formatUserInfoUseCase)
    }

    @Provides
    @Singleton
    fun provideFormatUserInfoUseCase(): FormatUserInfoUseCase {
        return FormatUserInfoUseCase()
    }
}