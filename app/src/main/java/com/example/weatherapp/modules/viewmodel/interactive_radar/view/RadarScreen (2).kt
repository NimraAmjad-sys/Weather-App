package com.example.weatherapp.modules.viewmodel.interactive_radar.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

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
                        "Search",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Search Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF1F3F5)
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
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text("Search", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                    Icon(Icons.Default.Mic, contentDescription = null, tint = Color.Gray)
                }
            }

            // Map Area with Radar Overlay
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                // Placeholder Map Image
                Image(
                    painter = painterResource(id = R.drawable.cloud_bg_png), // Using existing cloud bg as placeholder for map
                    contentDescription = "Map Placeholder",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.5f
                )

                // Simulated Map Lines (to make it look like a map)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Draw a grid or some lines to simulate a map
                }

                // Radar Circle Overlay
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(300.dp)) {
                        drawCircle(
                            color = Color(0xFF3B82F6).copy(alpha = 0.2f),
                            radius = size.minDimension / 2
                        )
                        drawCircle(
                            color = Color(0xFF3B82F6),
                            radius = size.minDimension / 2,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                    
                    // Central Marker
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF3B82F6),
                        modifier = Modifier.size(32.dp).offset(y = (-16).dp)
                    )
                    
                    Text(
                        "New York",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.offset(y = 10.dp)
                    )
                }

                // Additional simulated markers
                Marker(Modifier.align(Alignment.Center).offset(x = 60.dp, y = 40.dp))
                Marker(Modifier.align(Alignment.Center).offset(x = 50.dp, y = 70.dp))
                Marker(Modifier.align(Alignment.Center).offset(x = 80.dp, y = 60.dp))
            }

            // Bottom Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Park Slope", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Text("Paris, London", color = Color.Gray, fontSize = 14.sp)
                    }
                    Text("72°", fontSize = 36.sp, fontWeight = FontWeight.Light)
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
        tint = Color(0xFF5A9FFF),
        modifier = modifier.size(24.dp)
    )
}

// BasicTextField helper since I don't want to import full foundation text for now if possible
@Composable
fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    decorationBox: @Composable (@Composable () -> Unit) -> Unit
) {
    androidx.compose.foundation.text.BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        decorationBox = decorationBox
    )
}

@Preview(showBackground = true)
@Composable
fun RadarScreenPreview() {
    RadarScreen(onBackClick = {})
}
