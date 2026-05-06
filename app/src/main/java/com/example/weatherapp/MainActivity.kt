package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.weatherapp.ui.startpage.StartPage
import com.example.weatherapp.ui.viewmodel.locations.view.LocationsScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                var currentScreen by remember { mutableStateOf("start") }

                when (currentScreen) {
                    "start" -> StartPage(
                        onGetStartedClick = { currentScreen = "locations" }
                    )
                    "locations" -> LocationsScreen(
                        onBackClick = { currentScreen = "start" }
                    )
                }
            }
        }
    }
}
