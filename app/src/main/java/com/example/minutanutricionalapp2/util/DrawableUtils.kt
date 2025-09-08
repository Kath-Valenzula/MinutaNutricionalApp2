package com.example.minutanutricionalapp2.util

import android.content.Context

fun drawableIdByName(context: Context, rawName: String): Int {
    val base = rawName.substringBeforeLast('.') 
    return context.resources.getIdentifier(base, "drawable", context.packageName)
}
