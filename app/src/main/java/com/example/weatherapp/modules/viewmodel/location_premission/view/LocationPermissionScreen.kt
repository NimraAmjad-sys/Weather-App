package com.example.weatherapp.modules.viewmodel.location_premission.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.modules.viewmodel.location_premission.viewmodel.LocationPermissionViewModel

@Composable
fun LocationPermissionScreen(
    onAllowClick: () -> Unit,
    onSkipClick: () -> Unit,
    viewModel: LocationPermissionViewModel = viewModel()
) {
    val weatherData by viewModel.weatherData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF81D4FA), Color(0xFF4FC3F7))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "New York City",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("C", color = Color(0xFF4FC3F7), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("F", color = Color.White, modifier = Modifier.padding(end = 8.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.size(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = Color(0xFFFFD54F),
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = 20.dp, y = (-10).dp)
                )
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(140.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val currentTemp = weatherData?.currentWeather?.temperature?.toInt() ?: 22
            Text(
                text = "${currentTemp}°",
                fontSize = 96.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraLight
            )
            Text(
                text = "Sunny",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Feel like ${currentTemp + 1}°C",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Hourly Forecast",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ForecastItem("Now", "23°")
                        ForecastItem("1pm", "22°")
                        ForecastItem("2pm", "24°")
                        ForecastItem("3pm", "23°")
                        ForecastItem("4pm", "22°")
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Allow \"SkyPulse\" to access your location?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "To show local weather, wind, end alerts.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.6f))
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {
                        TextButton(
                            onClick = onSkipClick,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Text(
                                "Don't Allow",
                                color = Color(0xFF007AFF),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        
                        VerticalDivider(
                            color = Color.LightGray.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxHeight().width(0.5.dp)
                        )
                        
                        TextButton(
                            onClick = onAllowClick,
                            modifier = Modifier.weight(1.2f).fillMaxHeight()
                        ) {
                            Text(
                                "Allow While Using App",
                                color = Color(0xFF007AFF),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastItem(time: String, temp: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = time, color = Color.White, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = null,
            tint = Color(0xFFFFD54F),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = temp, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = "Sun", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPermissionScreenPreview() {
    LocationPermissionScreen(onAllowClick = {}, onSkipClick = {})
}
