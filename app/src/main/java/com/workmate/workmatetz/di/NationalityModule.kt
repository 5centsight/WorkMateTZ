package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.repository.NationalitiesRepositoryImpl
import com.workmate.workmatetz.domain.repository.NationalitiesRepository
import com.workmate.workmatetz.domain.usecases.GetNationalitiesUseCase
import org.koin.dsl.module

val nationalityModule = module {
    single<NationalitiesRepository> { NationalitiesRepositoryImpl() }
    single { GetNationalitiesUseCase(get()) }
}