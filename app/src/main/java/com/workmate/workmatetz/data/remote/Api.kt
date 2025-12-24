package com.workmate.workmatetz.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Api {
    private const val BASE_URL = "https://randomuser.me/"
    private val contentType = "application/json".toMediaType()

    fun provideJson(): Json = Json {
        isLenient = true
        explicitNulls = false
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    fun apiService(retrofit: Retrofit): RandomUserApi =
        retrofit.create(RandomUserApi::class.java)
}