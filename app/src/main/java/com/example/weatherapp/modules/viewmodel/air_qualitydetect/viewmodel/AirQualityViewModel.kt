package com.example.weatherapp.modules.viewmodel.air_qualitydetect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.modules.viewmodel.air_qualitydetect.model.AirQualityResponse
import com.example.weatherapp.modules.viewmodel.air_qualitydetect.services.AirQualityService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirQualityViewModel : ViewModel() {

    private val _airQualityData = MutableStateFlow<AirQualityResponse?>(null)
    val airQualityData: StateFlow<AirQualityResponse?> = _airQualityData

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.waqi.info/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(AirQualityService::class.java)
    private val apiToken = "demo" // Using 'demo' token for Air Quality API

    fun fetchAirQuality(city: String = "London") {
        viewModelScope.launch {
            try {
                val response = service.getAirQuality(city, apiToken)
                if (response.status == "ok") {
                    _airQualityData.value = response
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
