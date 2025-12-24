package com.workmate.workmatetz.di

import android.content.Context
import com.workmate.workmatetz.data.local.UserDao
import com.workmate.workmatetz.data.local.UserDatabase
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
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        UserDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
}