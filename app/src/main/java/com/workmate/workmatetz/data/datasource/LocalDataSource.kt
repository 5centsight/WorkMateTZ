package com.workmate.workmatetz.data.datasource

import com.workmate.workmatetz.data.local.UserDao
import com.workmate.workmatetz.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val userDao: UserDao
) {
    suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserBySeed(seed: String): UserEntity? {
        return userDao.getUserBySeed(seed)
    }

    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun deleteUser(seed: String) {
        return userDao.deleteUser(seed)
    }
}