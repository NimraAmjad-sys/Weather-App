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
                        Text("Weather Dash", fontWeight = FontWeight.Black, fontSize = 22.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                // ACTIONS REMOVED (Search/Settings buttons gone from top)
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
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
        containerColor = MaterialTheme.colorScheme.background
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
                // Main Weather Card - Defaulted to Lahore
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { onLocationClick("Lahore", 31.5204, 74.3587) },
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF1E88E5), Color(0xFF4FC3F7))
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Text("Lahore, Pakistan", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
                            Text("Mostly Clear", color = Color.White.copy(alpha = 0.8f), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.weight(1f))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("28°", color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Light)
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text("H: 30° L: 22°", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text("Feels like 29°", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                                }
                            }
                        }
                        Text("☀️", fontSize = 80.sp, modifier = Modifier.align(Alignment.TopEnd))
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
                // News/Alert Section
                Text("Weather Alerts", fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth().clickable { onWarningClick() },
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(56.dp).background(Color(0xFFFFF3E0), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFF9800))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Severe Warning", fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                            Text("Storm approaching Lahore area", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}

@Composable
fun DashboardActionCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.height(110.dp).clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onRadarClick: () -> Unit,
    onAirQualityClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 0.dp) {
        NavigationBarItem(
            selected = true,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.DarkGray) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = false,
            onClick = onRadarClick,
            icon = { Icon(Icons.Default.Map, contentDescription = "Radar", tint = Color.DarkGray) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onAirQualityClick,
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

@Preview(showBackground = true)
@Composable
fun LocationsScreenPreview() {
    LocationsScreen(
        onBackClick = {},
        onSearchClick = {},
        onAirQualityClick = {},
        onWarningClick = {},
        onSettingClick = {},
        onRadarClick = {},
        onCompareClick = {},
        onLocationClick = { _, _, _ -> }
    )
}
