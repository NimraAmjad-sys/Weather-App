package com.example.weatherapp.modules.viewmodel.interactive_radar.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadarScreen(
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Interactive Radar",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text("Search for Lahore area...", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                    Icon(Icons.Default.Mic, contentDescription = null, tint = Color.Gray)
                }
            }

            // Large Map Area with Radar Overlay
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFE1F5FE)) // Map-like base color
            ) {
                // Map/Graph Grid Visual
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val gridColor = Color.LightGray.copy(alpha = 0.6f)
                    val spacing = 50.dp.toPx()
                    
                    // Draw Vertical Lines
                    for (x in 0 until (size.width / spacing).toInt() + 1) {
                        drawLine(gridColor, start = Offset(x * spacing, 0f), end = Offset(x * spacing, size.height), strokeWidth = 1f)
                    }
                    // Draw Horizontal Lines
                    for (y in 0 until (size.height / spacing).toInt() + 1) {
                        drawLine(gridColor, start = Offset(0f, y * spacing), end = Offset(size.width, y * spacing), strokeWidth = 1f)
                    }
                }

                // Radar Circles & Lahore Highlight
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(320.dp)) {
                        drawCircle(
                            color = Color(0xFF1E88E5).copy(alpha = 0.1f),
                            radius = size.minDimension / 2
                        )
                        drawCircle(
                            color = Color(0xFF1E88E5).copy(alpha = 0.3f),
                            radius = size.minDimension / 2,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                    
                    // HIGHLIGHT LAHORE
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFFD32F2F), // Bold red for highlight
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            "Lahore",
                            color = Color.Black,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                // Additional simulated markers near Lahore
                Marker(Modifier.align(Alignment.Center).offset(x = 60.dp, y = 40.dp))
                Marker(Modifier.align(Alignment.Center).offset(x = (-50).dp, y = 70.dp))
            }

            // Bottom Info Card (Lahore)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, null, tint = Color(0xFF1E88E5), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Lahore City",
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            "Punjab, Pakistan",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 28.dp)
                        )
                    }
                    Text(
                        "28°",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun Marker(modifier: Modifier = Modifier) {
    Icon(
        Icons.Default.LocationOn,
        contentDescription = null,
        tint = Color(0xFF1E88E5).copy(alpha = 0.6f),
        modifier = modifier.size(28.dp)
    )
}

@Composable
fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    decorationBox: @Composable (@Composable () -> Unit) -> Unit
) {
    androidx.compose.foundation.text.BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        decorationBox = decorationBox
    )
}

@Preview(showBackground = true)
@Composable
fun RadarScreenPreview() {
    RadarScreen(onBackClick = {})
}
