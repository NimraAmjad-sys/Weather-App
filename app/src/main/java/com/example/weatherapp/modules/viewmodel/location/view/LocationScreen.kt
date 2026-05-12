package com.example.weatherapp.modules.viewmodel.location.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.modules.viewmodel.location.viewmodel.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    cityName: String = "London",
    latitude: Double = 51.5074,
    longitude: Double = 0.1278,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onRadarClick: () -> Unit = {},
    onHourlyClick: () -> Unit = {},
    onWeeklyClick: () -> Unit = {},
    viewModel: LocationViewModel = viewModel()
) {
    val weatherData by viewModel.weatherData.collectAsState()

    LaunchedEffect(latitude, longitude) {
        viewModel.fetchWeather(latitude, longitude)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(cityName, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                FeaturedWeatherCard(
                    cityName = cityName,
                    temp = weatherData?.currentWeather?.temperature?.toInt() ?: 32,
                    condition = "Party Cloudy"
                )
            }

            item {
                // Interactive Radar Card
                SectionTitle("Interactive Radar")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clickable { onRadarClick() },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .align(Alignment.CenterStart)
                        ) {
                            Text(
                                "Live Map",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E88E5),
                                fontSize = 16.sp
                            )
                            Text(
                                "Check real-time radar",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            tint = Color(0xFF1E88E5).copy(alpha = 0.3f),
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.CenterEnd)
                                .padding(end = 10.dp)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionTitle("Today Info")
                    TextButton(onClick = onHourlyClick) {
                        Text("Hourly Forecast", color = Color(0xFF5A9FFF))
                    }
                }
                TodayInfoTabs()
                Spacer(modifier = Modifier.height(10.dp))
                HourlyForecastRow(onClick = onHourlyClick)
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionTitle("7-Days Forecast")
                    TextButton(onClick = onWeeklyClick) {
                        Text("View Full", color = Color(0xFF5A9FFF))
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onWeeklyClick() },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F4F9))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        repeat(3) { index ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(if (index == 0) "Today" else if (index == 1) "Tomorrow" else "Wed", fontWeight = FontWeight.Medium)
                                Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                                Text("22°/18°", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            item {
                SectionTitle("Wind")
                WindCard(
                    windSpeed = weatherData?.currentWeather?.windSpeed ?: 124.0,
                    pressure = weatherData?.hourly?.pressureMsl?.firstOrNull() ?: 972.1
                )
            }

            item {
                SectionTitle("Sun Condition")
                SunConditionCard(
                    uvIndex = weatherData?.hourly?.uvIndex?.firstOrNull() ?: 0.3
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun FeaturedWeatherCard(cityName: String, temp: Int, condition: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5A9FFF))
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cloud,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(cityName, color = Color.White, fontWeight = FontWeight.Medium)
                    Text(condition, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                }
            }
            Text("${temp}°", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun TodayInfoTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8EEF7), RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem("Yesterday", false)
        TabItem("Today", true)
        TabItem("Tomorrow", false)
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color(0xFF5A9FFF) else Color.Transparent,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
fun HourlyForecastRow(onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ForecastItem("Now", "32°", Icons.Default.WbSunny)
        ForecastItem("1pm", "30°", Icons.Default.CloudQueue)
        ForecastItem("2pm", "29°", Icons.Default.CloudQueue)
        ForecastItem("3pm", "27°", Icons.Default.CloudQueue)
        ForecastItem("4pm", "32°", Icons.Default.WbSunny)
    }
}

@Composable
fun ForecastItem(time: String, temp: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(time, color = Color.Gray, fontSize = 12.sp)
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (icon == Icons.Default.WbSunny) Color(0xFFFFD54F) else Color(0xFF81D4FA),
            modifier = Modifier.size(28.dp).padding(vertical = 4.dp)
        )
        Text(temp, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun WindCard(windSpeed: Double, pressure: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF7))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Condition", color = Color.Gray, fontSize = 12.sp)
            Text("Pressure", color = Color.Black, fontWeight = FontWeight.Medium)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.Air,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.DarkGray
                )
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Wind", color = Color.Gray, fontSize = 12.sp)
                    Text("${windSpeed}mph", fontWeight = FontWeight.Bold)
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Barometer", color = Color.Gray, fontSize = 12.sp)
                    Text("${pressure}mBar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SunConditionCard(uvIndex: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF7))
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Conditions", color = Color.Gray, fontSize = 12.sp)
                Text("Sun", fontWeight = FontWeight.Medium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("UV index", color = Color.Gray, fontSize = 12.sp)
                Text("$uvIndex", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    LocationScreen(cityName = "London", onBackClick = {})
}
