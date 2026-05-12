package com.example.weatherapp.modules.viewmodel.weekly_forecast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.modules.viewmodel.location.services.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class DailyForecastItem(
    val day: String,
    val date: String,
    val weatherCode: Int,
    val precipitation: Int,
    val maxTemp: Int,
    val minTemp: Int
)

class WeeklyForecastViewModel : ViewModel() {
    private val _weeklyData = MutableStateFlow<List<DailyForecastItem>>(emptyList())
    val weeklyData: StateFlow<List<DailyForecastItem>> = _weeklyData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherService::class.java)

    fun fetchWeeklyForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = service.getFullWeather(
                    lat = lat,
                    lon = lon,
                    daily = "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max"
                )

                val items = mutableListOf<DailyForecastItem>()
                response.daily?.let { daily ->
                    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val displayDateFormatter = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH)
                    val dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)

                    for (i in 0 until daily.time.size) {
                        val date = LocalDate.parse(daily.time[i], dateFormatter)
                        items.add(
                            DailyForecastItem(
                                day = date.format(dayFormatter),
                                date = date.format(displayDateFormatter),
                                weatherCode = daily.weatherCode[i],
                                precipitation = daily.precipitationProbabilityMax?.get(i) ?: 0,
                                maxTemp = daily.temperature2mMax?.get(i)?.toInt() ?: 0,
                                minTemp = daily.temperature2mMin?.get(i)?.toInt() ?: 0
                            )
                        )
                    }
                }
                _weeklyData.value = items
            } catch (_: Exception) {
                _weeklyData.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
