package com.example.weatherapp.modules.viewmodel.location.services

import com.example.weatherapp.modules.viewmodel.location.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("v1/forecast")
    suspend fun getFullWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current_weather") current: Boolean = true,
        @Query("hourly") hourly: String = "temperature_2m,weather_code,pressure_msl,uv_index",
        @Query("daily") daily: String = "weather_code"
    ): WeatherResponse
}
