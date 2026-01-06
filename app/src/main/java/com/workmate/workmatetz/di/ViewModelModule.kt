package com.workmate.workmatetz.di

import com.workmate.workmatetz.domain.usecases.CopyUserToClipboardUseCase
import com.workmate.workmatetz.domain.usecases.DeleteUserUseCase
import com.workmate.workmatetz.domain.usecases.GetAllUsersUseCase
import com.workmate.workmatetz.domain.usecases.GetNationalitiesUseCase
import com.workmate.workmatetz.domain.usecases.GetRandomUserUseCase
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import com.workmate.workmatetz.domain.usecases.ShareUserUseCase
import com.workmate.workmatetz.presentation.screens.home.HomeViewModel
import com.workmate.workmatetz.presentation.screens.userDetails.UserDetailsViewModel
import com.workmate.workmatetz.presentation.screens.usersList.UsersListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideHomeViewModel(
        getNationalitiesUseCase: GetNationalitiesUseCase
    ): HomeViewModel = HomeViewModel(getNationalitiesUseCase)

    @Provides
    @ViewModelScoped
    fun provideUsersListViewModel(
        getRandomUserUseCase: GetRandomUserUseCase,
        getAllUsersUseCase: GetAllUsersUseCase,
        deleteUserUseCase: DeleteUserUseCase,
        shareUserUseCase: ShareUserUseCase,
        copyUserToClipboardUseCase: CopyUserToClipboardUseCase
    ): UsersListViewModel =
        UsersListViewModel(
            getRandomUserUseCase,
            getAllUsersUseCase,
            deleteUserUseCase,
            shareUserUseCase,
            copyUserToClipboardUseCase
        )

    @Provides
    @ViewModelScoped
    fun providesUserDetailsViewModel(
        getUserBySeedUseCase: GetUserBySeedUseCase
    ): UserDetailsViewModel = UserDetailsViewModel(getUserBySeedUseCase)
}