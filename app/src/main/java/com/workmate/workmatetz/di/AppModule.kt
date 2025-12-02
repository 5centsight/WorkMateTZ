package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.datasource.LocalDataSource
import com.workmate.workmatetz.data.datasource.RemoteDataSource
import com.workmate.workmatetz.data.local.UserDatabase
import com.workmate.workmatetz.data.remote.Api
import com.workmate.workmatetz.data.remote.RandomUserApi
import com.workmate.workmatetz.data.repository.NationalitiesRepositoryImpl
import com.workmate.workmatetz.data.repository.RandomUserRepositoryImpl
import com.workmate.workmatetz.domain.repository.NationalitiesRepository
import com.workmate.workmatetz.domain.repository.RandomUserRepository
import com.workmate.workmatetz.domain.usecases.DeleteUserUseCase
import com.workmate.workmatetz.domain.usecases.GetAllUsersUseCase
import com.workmate.workmatetz.domain.usecases.GetNationalitiesUseCase
import com.workmate.workmatetz.domain.usecases.GetRandomUserUseCase
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import com.workmate.workmatetz.presentation.screens.home.HomeViewModel
import com.workmate.workmatetz.presentation.screens.userDetails.UserDetailsViewModel
import com.workmate.workmatetz.presentation.screens.usersList.UsersListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<RandomUserApi> { Api.apiService }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }

    single { UserDatabase.getInstance(get()) }
    single { get<UserDatabase>().userDao() }

    single<RandomUserRepository> { RandomUserRepositoryImpl(get(), get()) }
    single<NationalitiesRepository> { NationalitiesRepositoryImpl() }

    single { GetRandomUserUseCase(get()) }
    single { GetUserBySeedUseCase(get()) }
    single { GetNationalitiesUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    single { DeleteUserUseCase(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { UsersListViewModel(get(), get(), get()) }
    viewModel { UserDetailsViewModel(get()) }
}