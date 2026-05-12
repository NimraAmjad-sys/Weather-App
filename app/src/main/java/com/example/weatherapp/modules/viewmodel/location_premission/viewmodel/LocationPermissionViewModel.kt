package com.example.weatherapp.modules.viewmodel.location_premission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.modules.viewmodel.location_premission.model.PermissionOpenMeteoResponse
import com.example.weatherapp.modules.viewmodel.location_premission.services.PermissionWeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocationPermissionViewModel : ViewModel() {

    private val _weatherData = MutableStateFlow<PermissionOpenMeteoResponse?>(null)
    val weatherData: StateFlow<PermissionOpenMeteoResponse?> = _weatherData

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PermissionWeatherService::class.java)

    fun fetchWeather(lat: Double = 40.7128, lon: Double = -74.0060) { // Default New York
        viewModelScope.launch {
            try {
                val response = service.getForecast(lat, lon)
                _weatherData.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
