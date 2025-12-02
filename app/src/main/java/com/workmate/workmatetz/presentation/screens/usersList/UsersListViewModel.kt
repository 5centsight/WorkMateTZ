package com.workmate.workmatetz.presentation.screens.usersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.domain.usecases.DeleteUserUseCase
import com.workmate.workmatetz.domain.usecases.GetAllUsersUseCase
import com.workmate.workmatetz.domain.usecases.GetRandomUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val getRandomUserUseCase: GetRandomUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersListUiState>(UsersListUiState.Idle)
    val uiState: StateFlow<UsersListUiState> = _uiState.asStateFlow()

    private val _deleteState = MutableStateFlow<DeleteState>(DeleteState.Idle)
    val deleteState: StateFlow<DeleteState> = _deleteState.asStateFlow()

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getAllUsersUseCase().collect { users ->
                _allUsers.value = users
            }
        }
    }

    fun generateUser(gender: String, nationality: String) {
        viewModelScope.launch {
            _uiState.value = UsersListUiState.Loading
            getRandomUserUseCase(gender, nationality).onSuccess {
                _uiState.value = UsersListUiState.Success
            }.onFailure { error ->
                _uiState.value = UsersListUiState.Error(
                    error.message ?: "Failed to generate user"
                )
            }
        }
    }

    fun deleteUser(seed: String) {
        viewModelScope.launch {
            _deleteState.value = DeleteState.Deleting(seed)
            deleteUserUseCase(seed).onSuccess {
            }.onFailure {
                _deleteState.value = DeleteState.Error(
                    seed = seed,
                    message = "Failed to delete user"
                )
            }
        }
    }
}