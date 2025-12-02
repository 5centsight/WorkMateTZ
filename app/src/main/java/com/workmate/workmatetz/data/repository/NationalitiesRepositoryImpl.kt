package com.workmate.workmatetz.data.repository

import com.workmate.workmatetz.domain.repository.NationalitiesRepository

class NationalitiesRepositoryImpl : NationalitiesRepository {
    override suspend fun getNationalitiesList(): Result<List<Pair<String, String>>> {
        return Result.success(
            listOf(
                "au" to "Australian",
                "br" to "Brazilian",
                "ca" to "Canadian",
                "ch" to "Swiss",
                "de" to "German",
                "dk" to "Danish",
                "es" to "Spanish",
                "fi" to "Finnish",
                "fr" to "French",
                "gb" to "British",
                "ie" to "Irish",
                "in" to "Indian",
                "ir" to "Iranian",
                "mx" to "Mexican",
                "nl" to "Dutch",
                "no" to "Norwegian",
                "nz" to "New Zealander",
                "rs" to "Serbian",
                "tr" to "Turkish",
                "ua" to "Ukrainian",
                "us" to "American"
            )
        )
    }
}