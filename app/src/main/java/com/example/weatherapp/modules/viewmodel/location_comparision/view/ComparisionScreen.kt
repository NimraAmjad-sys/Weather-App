package com.example.weatherapp.modules.viewmodel.location_comparision.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.modules.viewmodel.location_comparision.viewmodel.ComparisionViewModel
import com.example.weatherapp.modules.viewmodel.location_comparision.viewmodel.CityWeather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparisionScreen(
    onBackClick: () -> Unit,
    viewModel: ComparisionViewModel = viewModel()
) {
    val cities by viewModel.comparisionList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Comparison", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color(0xFFF8F9FD)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Compare Weather",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            LazyRow(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cities) { city ->
                    ComparisionCard(city)
                }
            }
        }
    }
}

@Composable
fun ComparisionCard(city: CityWeather) {
    val isDark = (city.weather?.currentWeather?.temperature ?: 0.0) < 15.0
    val bgBrush = if (isDark) {
        Brush.verticalGradient(listOf(Color(0xFF2C3E50), Color(0xFF000000)))
    } else {
        Brush.verticalGradient(listOf(Color(0xFF5A9FFF), Color(0xFF81D4FA)))
    }

    Card(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight()
            .padding(bottom = 20.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgBrush)
                .padding(24.dp)
        ) {
            if (city.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            city.cityName,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = if (isDark) Icons.Default.Cloud else Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        "${city.weather?.currentWeather?.temperature?.toInt() ?: "--"}°C",
                        color = Color.White,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Light
                    )
                    
                    Text(
                        if (isDark) "Moderate Rain" else "Sunny",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Weather Details
                    WeatherDetailRow("Wind", "${city.weather?.currentWeather?.windSpeed ?: "--"} km/h")
                    WeatherDetailRow("Humidity", "45%") // Placeholder for humidity

                    Spacer(modifier = Modifier.height(24.dp))

                    // Hourly Forecast mini-view
                    Text("Hourly Forecast", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(5) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Now", color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp)
                                Text("22°", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(alpha = 0.7f))
        Text(value, color = Color.White, fontWeight = FontWeight.Medium)
    }
}
