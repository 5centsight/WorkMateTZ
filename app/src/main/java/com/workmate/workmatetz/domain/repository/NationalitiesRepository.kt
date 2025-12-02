package com.workmate.workmatetz.domain.repository

interface NationalitiesRepository {
    suspend fun getNationalitiesList(): Result<List<Pair<String, String>>>
}