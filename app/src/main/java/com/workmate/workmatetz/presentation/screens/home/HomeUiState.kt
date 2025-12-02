package com.workmate.workmatetz.presentation.screens.home

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val nationalities: List<Pair<String, String>>,
        val selectedGender: String,
        val selectedNationality: String
    ) : HomeUiState()

    data class Error(val message: String) : HomeUiState()
}