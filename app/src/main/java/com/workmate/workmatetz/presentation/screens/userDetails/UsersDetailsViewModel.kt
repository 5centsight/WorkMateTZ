package com.workmate.workmatetz.presentation.screens.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workmate.workmatetz.domain.usecases.GetUserBySeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserBySeedUseCase: GetUserBySeedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    private val _snackMessages: MutableSharedFlow<String> = MutableSharedFlow()
    val snackMessages = _snackMessages.asSharedFlow()

    fun loadUser(seed: String) {
        viewModelScope.launch {
            _uiState.value = UserDetailsUiState.Loading
            getUserBySeedUseCase(seed).onSuccess { user ->
                _uiState.value = UserDetailsUiState.Success(user)
            }.onFailure { error ->
                _uiState.value = UserDetailsUiState.Error("Failed to load user: ${error.message}")
                showMessage("Failed to load user: ${error.message}")
            }
        }
    }

    suspend fun showMessage(message: String) = _snackMessages.emit(message)
}