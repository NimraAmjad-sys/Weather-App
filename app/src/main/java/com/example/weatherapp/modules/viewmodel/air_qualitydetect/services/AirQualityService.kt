package com.example.weatherapp.modules.viewmodel.air_qualitydetect.services

import com.example.weatherapp.modules.viewmodel.air_qualitydetect.model.AirQualityResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AirQualityService {
    @GET("feed/{city}/")
    suspend fun getAirQuality(
        @Path("city") city: String,
        @Query("token") token: String
    ): AirQualityResponse

    @GET("feed/here/")
    suspend fun getAirQualityByLocation(
        @Query("token") token: String
    ): AirQualityResponse
}
