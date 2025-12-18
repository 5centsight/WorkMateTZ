package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.repository.RandomUserRepositoryImpl
import com.workmate.workmatetz.domain.repository.RandomUserRepository
import com.workmate.workmatetz.domain.usecases.DeleteUserUseCase
import com.workmate.workmatetz.domain.usecases.GetAllUsersUseCase
import com.workmate.workmatetz.domain.usecases.GetRandomUserUseCase
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import org.koin.dsl.module

val userFeatureModule = module {
    single<RandomUserRepository> { RandomUserRepositoryImpl(get(), get()) }

    single { GetRandomUserUseCase(get()) }
    single { GetUserBySeedUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    single { DeleteUserUseCase(get()) }
}