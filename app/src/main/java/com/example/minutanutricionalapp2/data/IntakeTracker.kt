package com.example.minutanutricionalapp2.data

import com.example.minutanutricionalapp2.model.NutritionTotals

object IntakeTracker {
    private val items = mutableListOf<NutritionTotals>()

    fun add(n: NutritionTotals) { items += n }
    fun clear() = items.clear()

    val totals: NutritionTotals
        get() = items.fold(NutritionTotals()) { acc, n ->
            NutritionTotals(
                calories = acc.calories + n.calories,
                proteinG = acc.proteinG + n.proteinG,
                carbsG   = acc.carbsG   + n.carbsG,
                fatG     = acc.fatG     + n.fatG
            )
        }
}
