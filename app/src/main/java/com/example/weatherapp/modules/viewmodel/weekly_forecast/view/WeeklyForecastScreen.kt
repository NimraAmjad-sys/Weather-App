package com.example.weatherapp.modules.viewmodel.weekly_forecast.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cloud
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
import com.example.weatherapp.modules.viewmodel.weekly_forecast.viewmodel.WeeklyForecastViewModel
import com.example.weatherapp.modules.viewmodel.weekly_forecast.viewmodel.DailyForecastItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeeklyForecastScreen(
    cityName: String,
    lat: Double,
    lon: Double,
    onBackClick: () -> Unit,
    viewModel: WeeklyForecastViewModel = viewModel()
) {
    val weeklyData by viewModel.weeklyData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(lat, lon) {
        viewModel.fetchWeeklyForecast(lat, lon)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = cityName.uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E88E5), // Professional Blue
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "7-Day Forecast",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = Color(0xFF212121)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        containerColor = Color(0xFFF8F9FD) 
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF5A9FFF)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    items(weeklyData) { item ->
                        DailyForecastRow(item)
                    }
                    // Bottom Spacer to ensure full visibility of the last card
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }
    }
}

@Composable
fun DailyForecastRow(item: DailyForecastItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Day and Date - High Visibility
            Column(modifier = Modifier.width(110.dp)) {
                Text(
                    text = item.day,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color(0xFF212121)
                )
                Text(
                    text = item.date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }

            // Weather Icon (Centered)
            Icon(
                imageVector = if (item.weatherCode < 3) Icons.Default.WbSunny else Icons.Default.Cloud,
                contentDescription = null,
                tint = if (item.weatherCode < 3) Color(0xFFFFD54F) else Color(0xFF81D4FA),
                modifier = Modifier.size(36.dp)
            )

            // Probability & Temperature
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (item.precipitation > 0) {
                    Text(
                        text = "${item.precipitation}%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4FC3F7),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
                Text(
                    text = "${item.maxTemp}°",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = " / ${item.minTemp}°",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeeklyForecastScreenPreview() {
    WeeklyForecastScreen(cityName = "London", lat = 51.5, lon = -0.12, onBackClick = {})
}
