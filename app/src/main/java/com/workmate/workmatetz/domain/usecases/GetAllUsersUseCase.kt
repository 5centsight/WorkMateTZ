package com.workmate.workmatetz.domain.usecases

import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.domain.repository.RandomUserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(
    private val repository: RandomUserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getAllUsers()
    }
}