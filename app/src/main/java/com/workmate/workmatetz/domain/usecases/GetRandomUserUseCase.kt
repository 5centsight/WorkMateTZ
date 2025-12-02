package com.workmate.workmatetz.domain.usecases

import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.domain.repository.RandomUserRepository

class GetRandomUserUseCase(
    private val repository: RandomUserRepository
) {
    suspend operator fun invoke(gender: String, nationality: String): Result<User> {
        return repository.getRandomPerson(gender, nationality)
    }
}