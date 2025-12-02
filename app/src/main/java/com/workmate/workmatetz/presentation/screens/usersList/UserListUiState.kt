package com.workmate.workmatetz.presentation.screens.usersList

sealed class UsersListUiState {
    object Idle : UsersListUiState()
    object Loading : UsersListUiState()
    object Success : UsersListUiState()
    data class Error(val message: String) : UsersListUiState()
}