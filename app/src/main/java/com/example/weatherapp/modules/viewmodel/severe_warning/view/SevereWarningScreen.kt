package com.example.weatherapp.modules.viewmodel.severe_warning.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SevereWarningScreen(onBackClick: () -> Unit) {
    BackHandler(onBack = onBackClick)
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image - Cloudy sky
        Image(
            painter = painterResource(id = R.drawable.cloud_bg_png),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Darkening overlay to simulate lock screen dimming
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar with Back Arrow
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // White Lock Icon at the top
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.height(140.dp))

                // Notification Card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        // Header Row: App Icon, App Name ("SKY PULSE"), and "now"
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_weather_png),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SKY PULSE",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.DarkGray,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "now",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Content Area: Warning Icon and Alert Message
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFFF9800), // More vibrant Orange
                                modifier = Modifier.size(24.dp).padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Severe Thunderstorm Warning",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "A strong storm is approaching Lahore in 15 minutes. Seek shelter immediately.",
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    color = Color.Black.copy(alpha = 0.9f),
                                    fontWeight = FontWeight.Medium
                                )
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
fun SevereWarningScreenPreview() {
    SevereWarningScreen(onBackClick = {})
}
