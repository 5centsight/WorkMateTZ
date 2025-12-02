package com.workmate.workmatetz.domain.usecases

import com.workmate.workmatetz.domain.repository.RandomUserRepository

class DeleteUserUseCase(
    private val repository: RandomUserRepository
) {
    suspend operator fun invoke(seed: String): Result<Unit> {
        return try {
            repository.deleteUser(seed)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}