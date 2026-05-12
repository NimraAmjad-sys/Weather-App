package com.example.weatherapp.modules.viewmodel.setting.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.modules.viewmodel.setting.viewmodel.SettingViewModel

@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    viewModel: SettingViewModel = viewModel()
) {
    val isFahrenheit by viewModel.isFahrenheit.collectAsState()
    val isWeatherAlertsEnabled by viewModel.isWeatherAlertsEnabled.collectAsState()
    val isDailyFeedbackEnabled by viewModel.isDailyFeedbackEnabled.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF)) // Light background
            .padding(horizontal = 20.dp)
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        
        // Large Header "setting"
        Text(
            text = "setting",
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFFE0E0E0), // Very light gray
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Main Settings Card
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Back Button and "settings" label
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1C1E)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Settings Items
                SettingItem(
                    title = "Temperature Metric",
                    trailing = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isFahrenheit) "F" else "C",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF003D7C),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Switch(
                                checked = isFahrenheit,
                                onCheckedChange = { viewModel.toggleTemperatureUnit(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF003D7C),
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.LightGray
                                )
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SettingItem(
                    title = "Device Weather Alerts",
                    trailing = {
                        Switch(
                            checked = isWeatherAlertsEnabled,
                            onCheckedChange = { viewModel.toggleWeatherAlerts(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF3B82F6),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color.LightGray
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SettingItem(
                    title = "Daily Feedback",
                    trailing = {
                        Switch(
                            checked = isDailyFeedbackEnabled,
                            onCheckedChange = { viewModel.toggleDailyFeedback(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF3B82F6),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color.LightGray
                            )
                        )
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFEEEEEE)
                )

                SettingItem(
                    title = "Theme",
                    trailing = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isDarkMode) "Dark" else "Light",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { viewModel.toggleDarkMode(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF1E293B),
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.LightGray
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF44474E)
        )
        trailing()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(onBackClick = {})
}
