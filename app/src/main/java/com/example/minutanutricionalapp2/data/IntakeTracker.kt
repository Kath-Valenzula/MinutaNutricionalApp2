package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.mutableStateListOf
import com.example.minutanutricionalapp2.model.NutritionTotals

object IntakeTracker {
    private val _items = mutableStateListOf<NutritionTotals>()
    val items: List<NutritionTotals> get() = _items

    fun add(n: NutritionTotals) {
        _items.add(n)
    }

    fun clear() {
        _items.clear()
    }

    val totals: NutritionTotals
        get() {
            var c = 0; var p = 0; var cb = 0; var f = 0
            for (x in _items) {
                c += x.calories
                p += x.proteinG
                cb += x.carbsG
                f += x.fatG
            }
            return NutritionTotals(c, p, cb, f)
        }
}
