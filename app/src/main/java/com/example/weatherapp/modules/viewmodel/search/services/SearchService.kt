package com.example.weatherapp.modules.viewmodel.search.services

import com.example.weatherapp.modules.viewmodel.search.model.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("v1/search")
    suspend fun searchLocations(
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): SearchResponse
}

object SearchService {
    private const val BASE_URL = "https://geocoding-api.open-meteo.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SearchApi by lazy {
        retrofit.create(SearchApi::class.java)
    }
}
