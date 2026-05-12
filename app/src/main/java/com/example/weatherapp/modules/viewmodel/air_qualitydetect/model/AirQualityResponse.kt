package com.example.weatherapp.modules.viewmodel.air_qualitydetect.model

data class AirQualityResponse(
    val status: String,
    val data: AirData
)

data class AirData(
    val aqi: Int,
    val city: City,
    val iaqi: Iaqi
)

data class City(
    val name: String
)

data class Iaqi(
    val pm25: Value?,
    val pm10: Value?,
    val o3: Value?,
    val no2: Value?
)

data class Value(
    val v: Double
)
