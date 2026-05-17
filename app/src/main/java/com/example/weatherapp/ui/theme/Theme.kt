package com.example.weatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003366),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color(0xFFE1E2E1),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF333333)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),
    onPrimary = Color.White,
    background = Color(0xFFF8F9FD),
    surface = Color.White,
    onBackground = Color(0xFF1A1C1E),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE8EEF7)
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
