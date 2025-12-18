package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.remote.Api.provideApiService
import com.workmate.workmatetz.data.remote.Api.provideJson
import com.workmate.workmatetz.data.remote.Api.provideOkHttpClient
import com.workmate.workmatetz.data.remote.Api.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    single { provideJson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideApiService(get()) }
}