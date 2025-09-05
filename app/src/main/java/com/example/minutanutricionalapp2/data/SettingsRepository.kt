package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SettingsRepository {
    var muted by mutableStateOf(false)
    var volume by mutableStateOf(0.4f) // 0..1
    var darkMode by mutableStateOf(false)

    var displayName by mutableStateOf("Usuario")
    var displayEmail by mutableStateOf("usuario@demo.cl")
}
