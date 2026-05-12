package com.example.weatherapp.modules.viewmodel.location_premission.model

import com.google.gson.annotations.SerializedName

data class PermissionOpenMeteoResponse(
    @SerializedName("current_weather")
    val currentWeather: PermissionCurrentWeather,
    val hourly: PermissionHourlyData
)

data class PermissionCurrentWeather(
    val temperature: Double,
    @SerializedName("weather_code")
    val weatherCode: Int
)

data class PermissionHourlyData(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>
)
