package com.workmate.workmatetz.domain.usecases

import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.domain.repository.RandomUserRepository

class GetUserBySeedUseCase(
    private val repository: RandomUserRepository
) {
    suspend operator fun invoke(seed: String): Result<User> {
        return repository.getExistedPerson(seed)
    }
}