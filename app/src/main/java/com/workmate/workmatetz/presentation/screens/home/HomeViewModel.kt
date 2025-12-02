package com.workmate.workmatetz.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workmate.workmatetz.domain.usecases.GetNationalitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNationalitiesUseCase: GetNationalitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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
                _uiState.value = HomeUiState.Error(error.message ?: "Failed to load nationalities")
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
}