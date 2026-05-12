package com.example.weatherapp.modules.viewmodel.location_premission.services

import com.example.weatherapp.modules.viewmodel.location_premission.model.PermissionOpenMeteoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PermissionWeatherService {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current_weather") current: Boolean = true,
        @Query("hourly") hourly: String = "temperature_2m,weather_code"
    ): PermissionOpenMeteoResponse
}
