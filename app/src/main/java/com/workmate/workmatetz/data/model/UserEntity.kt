package com.workmate.workmatetz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val seed: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val country: String,
    val email: String,
    val gender: String,
    val nationality: String,
    val date: String,
    val age: String,
    val position: String,
    val imageUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)