package com.workmate.workmatetz.domain.entity

data class User(
    val seed: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val country: String,
    val email: String,
    val gender: String?,
    val nationality: String?,
    val date: String,
    val age: String,
    val position: String,
    val imageUrl: String
)