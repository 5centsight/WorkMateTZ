package com.workmate.workmatetz.domain.repository

import com.workmate.workmatetz.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface RandomUserRepository {
    suspend fun getRandomPerson(gender: String, nat: String): Result<User>

    suspend fun getExistedPerson(seed: String): Result<User>

    fun getAllUsers(): Flow<List<User>>

    suspend fun deleteUser(seed: String)
}