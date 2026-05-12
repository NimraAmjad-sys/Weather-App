package com.example.weatherapp.modules.viewmodel.location.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather?,
    val hourly: HourlyData?,
    @SerializedName("daily")
    val daily: DailyData?
)

data class CurrentWeather(
    val temperature: Double,
    @Suppress("SpellCheckingInspection")
    @SerializedName("windspeed")
    val windSpeed: Double,
    @Suppress("SpellCheckingInspection")
    @SerializedName("weathercode")
    val weatherCode: Int,
    val time: String
)

data class HourlyData(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>,
    @SerializedName("pressure_msl")
    val pressureMsl: List<Double>,
    @SerializedName("uv_index")
    val uvIndex: List<Double>,
    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>? = null
)

data class DailyData(
    val time: List<String>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double>?,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double>?,
    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int>?
)
