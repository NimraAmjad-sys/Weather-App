package com.example.weatherapp.modules.viewmodel.setting.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingViewModel : ViewModel() {
    private val _isFahrenheit = MutableStateFlow(true)
    val isFahrenheit: StateFlow<Boolean> = _isFahrenheit.asStateFlow()

    private val _isWeatherAlertsEnabled = MutableStateFlow(true)
    val isWeatherAlertsEnabled: StateFlow<Boolean> = _isWeatherAlertsEnabled.asStateFlow()

    private val _isDailyFeedbackEnabled = MutableStateFlow(true)
    val isDailyFeedbackEnabled: StateFlow<Boolean> = _isDailyFeedbackEnabled.asStateFlow()

    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleTemperatureUnit(value: Boolean) {
        _isFahrenheit.value = value
    }

    fun toggleWeatherAlerts(value: Boolean) {
        _isWeatherAlertsEnabled.value = value
    }

    fun toggleDailyFeedback(value: Boolean) {
        _isDailyFeedbackEnabled.value = value
    }

    fun toggleDarkMode(value: Boolean) {
        _isDarkMode.value = value
    }
}
