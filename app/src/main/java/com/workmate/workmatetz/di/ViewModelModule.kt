package com.workmate.workmatetz.di

import com.workmate.workmatetz.presentation.screens.home.HomeViewModel
import com.workmate.workmatetz.presentation.screens.userDetails.UserDetailsViewModel
import com.workmate.workmatetz.presentation.screens.usersList.UsersListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { HomeViewModel(get()) }
    viewModel { UsersListViewModel(get(), get(), get()) }
    viewModel { UserDetailsViewModel(get()) }
}