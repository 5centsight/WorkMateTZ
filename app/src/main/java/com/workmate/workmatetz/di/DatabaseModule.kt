package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.local.UserDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { UserDatabase.getInstance(get()) }
    single { get<UserDatabase>().userDao() }
}