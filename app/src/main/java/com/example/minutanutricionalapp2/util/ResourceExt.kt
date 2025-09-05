package com.example.minutanutricionalapp2.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun drawableIdByName(name: String?): Int {
    val ctx = LocalContext.current
    return remember(name) {
        if (name.isNullOrBlank()) 0
        else ctx.resources.getIdentifier(name, "drawable", ctx.packageName)
    }
}
