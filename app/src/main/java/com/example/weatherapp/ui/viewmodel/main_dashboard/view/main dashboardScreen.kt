package com.example.weatherapp.ui.viewmodel.main_dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAirQualityClick: () -> Unit,
    onWarningClick: () -> Unit,
    onSettingClick: () -> Unit,
    onRadarClick: () -> Unit,
    onCompareClick: () -> Unit,
    onLocationClick: (String, Double, Double) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Weather Dash", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF1A1C1E))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                actions = {
                    IconButton(onClick = onSettingClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.Black)
                    }
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = { },
                onRadarClick = onRadarClick,
                onAirQualityClick = onAirQualityClick,
                onSearchClick = onSearchClick,
                onSettingClick = onSettingClick
            )
        },
        containerColor = Color(0xFFF8F9FD)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { Spacer(modifier = Modifier.height(10.dp)) }
            
            item {
                // Main Weather Card - Premium Design
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { onLocationClick("London", 51.5074, 0.1278) },
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF4FC3F7), Color(0xFF1976D2))
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Text("London, UK", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("Partly Cloudy", color = Color.White.copy(alpha = 0.8f), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.weight(1f))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("32°", color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Light)
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text("H: 34° L: 28°", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                                    Text("Feels like 31°", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                                }
                            }
                        }
                        Text("☀️", fontSize = 70.sp, modifier = Modifier.align(Alignment.TopEnd))
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DashboardActionCard(
                        title = "Compare",
                        icon = Icons.Default.Compare,
                        color = Color(0xFFFFD54F),
                        modifier = Modifier.weight(1f),
                        onClick = onCompareClick
                    )
                    DashboardActionCard(
                        title = "Air Quality",
                        icon = Icons.Default.Air,
                        color = Color(0xFF81C784),
                        modifier = Modifier.weight(1f),
                        onClick = onAirQualityClick
                    )
                }
            }

            item {
                // News Section - Modern Style
                Text("Weather Insights", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth().clickable { onWarningClick() },
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(60.dp).background(Color(0xFFFFF3E0), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFF9800))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Severe Warning", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Storm approaching in 15 mins", color = Color.Gray, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun DashboardActionCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.height(100.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
fun BottomNavigationBar(onHomeClick: () -> Unit, onRadarClick: () -> Unit, onAirQualityClick: () -> Unit, onSearchClick: () -> Unit, onSettingClick: () -> Unit) {
    NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
        NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, selected = true, onClick = onHomeClick)
        NavigationBarItem(icon = { Icon(Icons.Default.Map, null) }, selected = false, onClick = onRadarClick)
        NavigationBarItem(icon = { Icon(Icons.Default.BarChart, null) }, selected = false, onClick = onAirQualityClick)
        NavigationBarItem(icon = { Icon(Icons.Default.Search, null) }, selected = false, onClick = onSearchClick)
        NavigationBarItem(icon = { Icon(Icons.Default.Settings, null) }, selected = false, onClick = onSettingClick)
    }
}
