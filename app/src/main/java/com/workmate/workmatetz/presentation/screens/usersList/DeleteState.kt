package com.workmate.workmatetz.presentation.screens.usersList

sealed class DeleteState {
    object Idle : DeleteState()
    data class Deleting(val seed: String) : DeleteState()
    data class Error(
        val seed: String,
        val message: String
    ) : DeleteState()
}