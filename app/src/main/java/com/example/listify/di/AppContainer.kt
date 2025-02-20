package com.example.listify.di

import com.example.listify.network.repository.NetworkSpotifyRepository
import com.example.listify.network.api.SpotifyApiService
import com.example.listify.network.repository.SpotifyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val spotifyRepository: SpotifyRepository
}

class DefaultAppContainer : AppContainer {
    private val tokenBaseURL = "https://accounts.spotify.com"
    private val spotifyBaseURL = "https://api.spotify.com"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val tokenRetrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(tokenBaseURL)
        .build()

    private val spotifyRetrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(spotifyBaseURL)
        .build()

    private val spotifyRetrofitService : SpotifyApiService by lazy {
        tokenRetrofit.create(SpotifyApiService::class.java)
    }

    private val tokenRetrofitService : SpotifyApiService by lazy {
        spotifyRetrofit.create(SpotifyApiService::class.java)
    }

    override val spotifyRepository: SpotifyRepository by lazy {
        NetworkSpotifyRepository(spotifyRetrofitService, tokenRetrofitService)
    }
}