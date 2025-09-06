package com.example.minutanutricionalapp2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.minutanutricionalapp2.data.SettingsRepository

private val LightColors = lightColorScheme(
    primary = Brand,
    onPrimary = OnBrand,
    primaryContainer = BrandContainer
)

private val DarkColors = darkColorScheme(
    primary = Brand,
    onPrimary = OnBrand,
    primaryContainer = BrandContainer
)

@Composable
fun MinutaNutricionalApp2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (SettingsRepository.darkMode) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
