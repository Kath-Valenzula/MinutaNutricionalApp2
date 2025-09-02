package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.minutanutricionalapp2.model.Nutrition
import com.example.minutanutricionalapp2.model.Totals

object IntakeTracker {
    var totals by mutableStateOf(Totals())
        private set

    fun add(n: Nutrition) { totals = totals + n }
    fun clear() { totals = Totals() }
}
