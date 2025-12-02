package com.workmate.workmatetz.presentation.screens.userDetails

import com.workmate.workmatetz.domain.entity.User

sealed class UserDetailsUiState {
    object Loading : UserDetailsUiState()
    data class Success(val user: User) : UserDetailsUiState()
    data class Error(val message: String) : UserDetailsUiState()
}