package com.example.weatherapp.modules.viewmodel.hourly_forecast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.modules.viewmodel.location.services.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class HourlyItem(
    val time: String,
    val temp: Int,
    val weatherCode: Int,
    val precipitation: Int
)

class HourlyForecastViewModel : ViewModel() {
    private val _hourlyData = MutableStateFlow<List<HourlyItem>>(emptyList())
    val hourlyData: StateFlow<List<HourlyItem>> = _hourlyData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherService::class.java)

    fun fetchHourlyForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Open-Meteo geocoding for now, using passed lat/lon
                val response = service.getFullWeather(
                    lat = lat,
                    lon = lon,
                    hourly = "temperature_2m,weather_code,precipitation_probability"
                )
                
                val items = mutableListOf<HourlyItem>()
                response.hourly?.let { hourly ->
                    // Map the first 24 hours
                    for (i in 0 until minOf(24, hourly.time.size)) {
                        items.add(
                            HourlyItem(
                                time = hourly.time[i].substringAfter("T"),
                                temp = hourly.temperature2m[i].toInt(),
                                weatherCode = hourly.weatherCode[i],
                                precipitation = hourly.precipitationProbability?.get(i) ?: 0
                            )
                        )
                    }
                }
                _hourlyData.value = items
            } catch (_: Exception) {
                _hourlyData.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
