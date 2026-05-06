package com.example.weatherapp.ui.viewmodel.locations.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("London", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                // Main Weather Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF81D4FA))
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                        Column {
                            Text("Chance of rain: 60%", color = Color.White, fontSize = 14.sp)
                            Text("Party Cloudy", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Text("Paris, London", color = Color.White, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("32", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                                Text("° F", color = Color.White, fontSize = 16.sp, modifier = Modifier.offset(y = (-10).dp))
                                Spacer(modifier = Modifier.width(20.dp))
                                Text("☀️ 0.3", color = Color.White, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(20.dp))
                                Text("💨 12.4mph", color = Color.White, fontSize = 14.sp)
                            }
                        }
                        // Weather Illustration (Sun and Cloud)
                        Column(modifier = Modifier.align(Alignment.TopEnd)) {
                            Text("☀️", fontSize = 40.sp)
                            Text("☁️", fontSize = 60.sp, modifier = Modifier.offset(y = (-20).dp))
                        }
                    }
                }
            }

            item {
                Text("News weather", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        // Dark background weather box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(Color(0xFF303030))
                        ) {
                            Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("☀️", fontSize = 24.sp)
                                    Text("☁️", fontSize = 32.sp, modifier = Modifier.offset(y = (-10).dp))
                                    Spacer(modifier = Modifier.weight(1f))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("32", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                        Text("° F", color = Color.White, fontSize = 12.sp, modifier = Modifier.offset(y = (-6).dp))
                                    }
                                }
                                Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                        Text("Paris, London", color = Color.White, fontSize = 12.sp)
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Row {
                                        Text("☀️ 0.3", color = Color.White, fontSize = 10.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("💨 12.4mph", color = Color.White, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                        // News text section
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Here's what to expect from Tuesday weather forecast",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("14 minutes ago", color = Color.Gray, fontSize = 12.sp)
                                Text("WC Channel", color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            item {
                Text("Calendar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        CalendarRow("Monday", "☀️")
                        CalendarRow("Tuesday", "⛅")
                        CalendarRow("Wednesday", "🌧️")
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CalendarRow(day: String, icon: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(day, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Text(icon, fontSize = 24.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun LocationsScreenPreview() {
    LocationsScreen(onBackClick = {})
}
