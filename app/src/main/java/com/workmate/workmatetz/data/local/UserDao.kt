package com.workmate.workmatetz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.workmate.workmatetz.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE seed = :seed")
    suspend fun getUserBySeed(seed: String): UserEntity?

    @Query("SELECT * FROM users ORDER BY timestamp DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM users WHERE seed = :seed")
    suspend fun deleteUser(seed: String)
}