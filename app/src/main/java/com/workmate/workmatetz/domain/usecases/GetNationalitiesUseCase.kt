package com.workmate.workmatetz.domain.usecases

import com.workmate.workmatetz.domain.repository.NationalitiesRepository

class GetNationalitiesUseCase(
    private val repository: NationalitiesRepository
) {
    suspend operator fun invoke(): Result<List<Pair<String, String>>> {
        return repository.getNationalitiesList()
    }
}