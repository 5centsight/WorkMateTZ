package com.workmate.workmatetz.data.remote

import com.workmate.workmatetz.data.dto.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("api/")
    suspend fun getNewPerson(
        @Query("gender") gender: String,
        @Query("nat") nationality: String,
        @Query("exc") exclude: String = "id,login,registered,cell"
    ): PersonResponse

    @GET("api/")
    suspend fun getExistedPerson(
        @Query("seed") seed: String,
        @Query("exc") exclude: String = "id,login,registered,cell"
    ): PersonResponse
}