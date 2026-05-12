package com.example.weatherapp.modules.viewmodel.hourly_forecast.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.modules.viewmodel.hourly_forecast.viewmodel.HourlyForecastViewModel
import com.example.weatherapp.modules.viewmodel.hourly_forecast.viewmodel.HourlyItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastScreen(
    cityName: String,
    lat: Double,
    lon: Double,
    onBackClick: () -> Unit,
    viewModel: HourlyForecastViewModel = viewModel()
) {
    val hourlyData by viewModel.hourlyData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(lat, lon) {
        viewModel.fetchHourlyForecast(lat, lon)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = cityName, 
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Tomorrow's Hourly", 
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        containerColor = Color(0xFF5A6A78) // Dark bluish gray matching image
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 20.dp)
                ) {
                    items(hourlyData) { item ->
                        HourlyRow(item)
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 16.dp),
                            thickness = 0.5.dp,
                            color = Color.White.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HourlyRow(item: HourlyItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Time
        Text(
            text = item.time,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(70.dp)
        )

        // Weather Icon
        Icon(
            imageVector = if (item.weatherCode < 3) Icons.Default.WbSunny else Icons.Default.Cloud,
            contentDescription = null,
            tint = if (item.weatherCode < 3) Color(0xFFFFD54F) else Color(0xFF81D4FA),
            modifier = Modifier.size(32.dp)
        )

        // Temperature
        Text(
            text = "${item.temp}°",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(50.dp)
        )

        // Precipitation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(60.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = null,
                tint = Color(0xFF4FC3F7),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${item.precipitation}%",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourlyForecastScreenPreview() {
    HourlyForecastScreen(cityName = "London", lat = 51.5, lon = -0.12, onBackClick = {})
}
