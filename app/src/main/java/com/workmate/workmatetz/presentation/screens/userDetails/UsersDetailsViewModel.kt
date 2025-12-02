package com.workmate.workmatetz.presentation.screens.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val getUserBySeedUseCase: GetUserBySeedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    fun loadUser(seed: String) {
        viewModelScope.launch {
            _uiState.value = UserDetailsUiState.Loading
            getUserBySeedUseCase(seed).onSuccess { user ->
                _uiState.value = UserDetailsUiState.Success(user)
            }.onFailure { error ->
                _uiState.value = UserDetailsUiState.Error(error.message ?: "Failed to load user")
            }
        }
    }
}