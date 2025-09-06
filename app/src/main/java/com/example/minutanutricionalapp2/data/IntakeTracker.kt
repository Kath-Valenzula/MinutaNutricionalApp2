package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class NutritionTotals(
    val calories: Int = 0,
    val proteinG: Int = 0,
    val carbsG: Int = 0,
    val fatG: Int = 0
)

object IntakeTracker {
    var totals by mutableStateOf(NutritionTotals())
        private set

    fun add(n: NutritionTotals) {
        totals = totals.copy(
            calories = totals.calories + n.calories,
            proteinG = totals.proteinG + n.proteinG,
            carbsG = totals.carbsG + n.carbsG,
            fatG = totals.fatG + n.fatG
        )
    }

    fun reset() { totals = NutritionTotals() }
}
