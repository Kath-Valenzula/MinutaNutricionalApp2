package com.example.minutanutricionalapp2.model

data class Nutrition(
    val calories: Int,
    val proteinG: Int,
    val carbsG:   Int,
    val fatG:     Int,
    val vitamins: Map<String, Int> = emptyMap()
)

// Acumulador de la ingesta del día
data class NutritionTotals(
    val calories: Int = 0,
    val proteinG: Int = 0,
    val carbsG:   Int = 0,
    val fatG:     Int = 0
)

fun Nutrition.toTotals(): NutritionTotals =
    NutritionTotals(calories, proteinG, carbsG, fatG)
