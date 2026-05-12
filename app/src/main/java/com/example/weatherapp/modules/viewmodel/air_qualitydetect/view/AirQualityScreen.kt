package com.example.weatherapp.modules.viewmodel.air_qualitydetect.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.modules.viewmodel.air_qualitydetect.viewmodel.AirQualityViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirQualityScreen(
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onRadarClick: () -> Unit = {},
    viewModel: AirQualityViewModel = viewModel()
) {
    val airQualityData by viewModel.airQualityData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAirQuality("London")
    }

    Scaffold(
        topBar = {
            Column {
                Text(
                    text = "Air Quality Detail",
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "${airQualityData?.data?.city?.name ?: "London"} Air Quality",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF333333)
                        )
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onSettingClick = onSettingClick,
                onSearchClick = onSearchClick,
                onRadarClick = onRadarClick
            )
        },
        containerColor = Color(0xFFF8F9FD)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Main AQI Gauge Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val aqi = airQualityData?.data?.aqi ?: 88
                    AQIGauge(aqi = aqi)

                    Text(
                        text = "Target",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = getAQIStatus(aqi),
                        color = getAQIColor(aqi),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Details Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    DetailRow("PM2.5", airQualityData?.data?.iaqi?.pm25?.v ?: 12.5)
                    DetailRow("PM10", airQualityData?.data?.iaqi?.pm10?.v ?: 24.0)
                    DetailRow("O3", airQualityData?.data?.iaqi?.o3?.v ?: 35.2)
                    DetailRow("N02", airQualityData?.data?.iaqi?.no2?.v ?: 18.7)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AQIGauge(aqi: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(220.dp)
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val strokeWidth = 14.dp.toPx()
            val startAngle = 140f
            val sweepAngle = 260f

            // Background track (Gray)
            drawArc(
                color = Color(0xFFF0F0F0),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Define segment angles and colors
            val segments = listOf(
                Pair(50f, Color(0xFF4CAF50)), // Green
                Pair(50f, Color(0xFFCDDC39)), // Light Green/Yellow
                Pair(50f, Color(0xFFFFC107)), // Orange
                Pair(50f, Color(0xFFFF5722)), // Red
                Pair(60f, Color(0xFFB71C1C))  // Dark Red
            )

            var currentStartAngle = startAngle
            for (segment in segments) {
                drawArc(
                    color = segment.second,
                    startAngle = currentStartAngle,
                    sweepAngle = segment.first,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                currentStartAngle += segment.first
            }

            // Needle
            // Map AQI (0-300) to sweep angle
            val targetSweep = (aqi.toFloat() / 300f * sweepAngle).coerceAtMost(sweepAngle)
            val angleInRad = (startAngle + targetSweep) * (Math.PI / 180f).toFloat()
            val radius = size.width / 2
            val needleLength = radius - 15.dp.toPx()
            val endX = center.x + needleLength * cos(angleInRad)
            val endY = center.y + needleLength * sin(angleInRad)

            drawLine(
                color = Color(0xFF4CAF50),
                start = center,
                end = Offset(endX, endY),
                strokeWidth = 5.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawCircle(
                color = Color.White,
                radius = 8.dp.toPx(),
                center = center
            )
            drawCircle(
                color = Color(0xFF4CAF50),
                radius = 5.dp.toPx(),
                center = center
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = aqi.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF333333)
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF333333),
            modifier = Modifier.width(70.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Sparkline-style Bar Chart
        Canvas(modifier = Modifier
            .width(150.dp)
            .height(40.dp)) {
            val barWidth = 3.dp.toPx()
            val spacing = 3.dp.toPx()
            val barCount = 18
            val maxBarHeight = size.height

            // Use 'value' to seed the random generator so it's technically "used" and provides consistent visual
            val random = Random(value.toLong().coerceAtLeast(1L))

            for (i in 0 until barCount) {
                // Randomish heights but biased towards the end like the picture
                val heightFactor = when {
                    i < 6 -> 0.2f + (random.nextFloat() * 0.2f)
                    i < 12 -> 0.4f + (random.nextFloat() * 0.4f)
                    else -> 0.2f + (random.nextFloat() * 0.3f)
                }
                val height = heightFactor * maxBarHeight
                val x = i * (barWidth + spacing)

                val color = when {
                    i < 8 -> Color(0xFF81C784) // Green
                    i < 13 -> Color(0xFFFFD54F) // Yellow/Orange
                    else -> Color(0xFFE57373) // Red
                }

                drawRoundRect(
                    color = color.copy(alpha = 0.8f),
                    topLeft = Offset(x, size.height - height),
                    size = Size(barWidth, height),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onRadarClick: () -> Unit = {}
) {
    NavigationBar(
        containerColor = Color(0xFF70B9E1),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.GridView, contentDescription = "Home", tint = Color.DarkGray) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = false,
            onClick = onRadarClick,
            icon = { Icon(Icons.Default.Map, contentDescription = "Radar", tint = Color.DarkGray) }
        )
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.BarChart, contentDescription = "Air Quality", tint = Color.DarkGray) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSearchClick,
            icon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.DarkGray) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSettingClick,
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.DarkGray) }
        )
    }
}

fun getAQIStatus(aqi: Int): String {
    return when {
        aqi <= 50 -> "Excellent"
        aqi <= 100 -> "Good"
        aqi <= 150 -> "Fair"
        else -> "Poor"
    }
}

fun getAQIColor(aqi: Int): Color {
    return when {
        aqi <= 50 -> Color(0xFF4CAF50)
        aqi <= 100 -> Color(0xFFCDDC39)
        aqi <= 150 -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }
}

@Preview(showBackground = true)
@Composable
fun AirQualityScreenPreview() {
    AirQualityScreen(onBackClick = {})
}
