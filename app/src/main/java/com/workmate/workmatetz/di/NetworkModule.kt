package com.workmate.workmatetz.di

import com.workmate.workmatetz.data.remote.Api
import com.workmate.workmatetz.data.remote.RandomUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Api.provideJson()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = Api.provideOkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit =
        Api.provideRetrofit(client, json)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RandomUserApi = Api.apiService(retrofit)
}