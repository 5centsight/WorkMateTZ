package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.datasource.LocalDataSource
import com.workmate.workmatetz.data.datasource.RemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
}