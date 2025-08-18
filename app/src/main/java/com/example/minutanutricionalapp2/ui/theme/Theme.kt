package com.example.minutanutricionalapp2.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PinkPrimary = Color(0xFFE91E63)   // Rosa
private val PinkSecondary = Color(0xFFF06292) // Rosa claro
private val PinkTertiary = Color(0xFFFFC1E3)  // Rosa muy claro

private val AppColors = lightColorScheme(
    primary = PinkPrimary,
    onPrimary = Color.White,
    secondary = PinkSecondary,
    onSecondary = Color.White,
    tertiary = PinkTertiary,
    surface = Color(0xFFFFF7FA),
    onSurface = Color(0xFF3A3A3A)
)

@Composable
fun MinutaNutricionalApp2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColors,
        typography = Typography(),
        content = content
    )
}
