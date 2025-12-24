package com.workmate.workmatetz.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workmate.workmatetz.domain.usecases.GetNationalitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNationalitiesUseCase: GetNationalitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _snackMessages: MutableSharedFlow<String> = MutableSharedFlow()
    val snackMessages = _snackMessages.asSharedFlow()

    init {
        loadNationalities()
    }

    private fun loadNationalities() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getNationalitiesUseCase().onSuccess { nationalities ->
                _uiState.value = HomeUiState.Success(
                    nationalities = nationalities,
                    selectedGender = "male",
                    selectedNationality = nationalities.first().first
                )
            }.onFailure { error ->
                val errorMessage = "Failed to load nationalities: ${error.message}"
                _uiState.value = HomeUiState.Error(errorMessage)
                showMessage(errorMessage)
            }
        }
    }

    fun updateSelectedGender(gender: String) {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success) {
            _uiState.value = currentState.copy(selectedGender = gender)
        }
    }

    fun updateSelectedNationality(nationality: String) {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success) {
            _uiState.value = currentState.copy(selectedNationality = nationality)
        }
    }

    suspend fun showMessage(message: String) = _snackMessages.emit(message)
}