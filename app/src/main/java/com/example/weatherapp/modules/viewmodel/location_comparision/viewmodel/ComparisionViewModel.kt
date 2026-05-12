package com.example.weatherapp.modules.viewmodel.location_comparision.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.modules.viewmodel.location.model.WeatherResponse
import com.example.weatherapp.modules.viewmodel.location.services.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class CityWeather(
    val cityName: String,
    val weather: WeatherResponse? = null,
    val isLoading: Boolean = true
)

class ComparisionViewModel : ViewModel() {
    private val _comparisionList = MutableStateFlow<List<CityWeather>>(emptyList())
    val comparisionList: StateFlow<List<CityWeather>> = _comparisionList.asStateFlow()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherService::class.java)

    private val cities = listOf(
        Pair("New York", Pair(40.7128, -74.0060)),
        Pair("London", Pair(51.5074, -0.1278)),
        Pair("Paris", Pair(48.8566, 2.3522)),
        Pair("Tokyo", Pair(35.6895, 139.6917)),
        Pair("San Francisco", Pair(37.7749, -122.4194))
    )

    init {
        fetchAllCitiesWeather()
    }

    private fun fetchAllCitiesWeather() {
        viewModelScope.launch {
            val initialList = cities.map { CityWeather(it.first) }
            _comparisionList.value = initialList

            cities.forEachIndexed { index, cityPair ->
                try {
                    val response = service.getFullWeather(cityPair.second.first, cityPair.second.second)
                    updateCityWeather(index, response)
                } catch (e: Exception) {
                    // Handle error silently for now
                }
            }
        }
    }

    private fun updateCityWeather(index: Int, response: WeatherResponse) {
        val currentList = _comparisionList.value.toMutableList()
        if (index < currentList.size) {
            currentList[index] = currentList[index].copy(weather = response, isLoading = false)
            _comparisionList.value = currentList
        }
    }
}
