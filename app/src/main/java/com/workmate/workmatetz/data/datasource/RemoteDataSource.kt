package com.workmate.workmatetz.data.datasource

import com.workmate.workmatetz.data.dto.PersonResponse
import com.workmate.workmatetz.data.remote.RandomUserApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: RandomUserApi) {

    suspend fun getRandomPerson(gender: String, nat: String): PersonResponse {
        return api.getNewPerson(gender, nat)
    }

    suspend fun getExistedPerson(seed: String): PersonResponse {
        return api.getExistedPerson(seed)
    }
}