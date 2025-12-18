package com.workmate.workmatetz.di

import org.koin.dsl.module

val appModule = module {
    includes(
        viewModelModule,
        databaseModule,
        networkModule,
        userFeatureModule,
        nationalityModule,
        dataSourceModule
    )
}