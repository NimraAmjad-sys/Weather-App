package com.example.weatherapp.modules.viewmodel.location_premission.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.delay

@Composable
fun LocationPermissionScreen(
    cityName: String = "Lahore",
    onAllowClick: () -> Unit,
    onSkipClick: () -> Unit,
    viewModel: LocationPermissionViewModel = viewModel()
) {
    val weatherData by viewModel.weatherData.collectAsState()
    var showPermissionDialog by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    // Success flow after Allow click
    if (isPermissionGranted) {
        LaunchedEffect(Unit) {
            delay(1500) // Brief success message
            onAllowClick()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isPermissionGranted) {
                        listOf(Color(0xFF43A047), Color(0xFF81C784)) // Green gradient for success
                    } else {
                        listOf(Color(0xFF1E88E5), Color(0xFF4FC3F7))
                    }
                )
            )
    ) {
        if (isPermissionGranted) {
            // Success "Notification" UI
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.CheckCircle, null, tint = Color.White, modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Location Access Granted!",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Setting up dashboard for $cityName...",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$cityName, Pakistan",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(80.dp))

                // Main Weather Icon Area
                Surface(
                    modifier = Modifier.size(180.dp),
                    shape = RoundedCornerShape(48.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Personalize Your\nWeather Experience",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )
                
                Text(
                    text = "Allow us to access your location to provide real-time updates for $cityName.",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp, start = 12.dp, end = 12.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Professional Action Button
                Button(
                    onClick = { showPermissionDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "Enable Location Access",
                        color = Color(0xFF1E88E5),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                
                TextButton(onClick = onSkipClick, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Skip for now", color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.Bold)
                }
            }
        }

        // The "Location Notification" / Permission Dialog
        if (showPermissionDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(64.dp).background(Color(0xFFE3F2FD), RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocationOn, null, tint = Color(0xFF1E88E5), modifier = Modifier.size(32.dp))
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Access Location?",
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "SkyPulse uses your location to show local weather and severe alerts in $cityName.",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            lineHeight = 22.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = { showPermissionDialog = false },
                                modifier = Modifier.weight(1f).height(50.dp),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Refuse", color = Color.Gray)
                            }
                            Button(
                                onClick = { 
                                    showPermissionDialog = false
                                    isPermissionGranted = true // Trigger success flow
                                },
                                modifier = Modifier.weight(1.2f).height(50.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                            ) {
                                Text("Allow Access", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPermissionScreenPreview() {
    LocationPermissionScreen(onAllowClick = {}, onSkipClick = {})
}
