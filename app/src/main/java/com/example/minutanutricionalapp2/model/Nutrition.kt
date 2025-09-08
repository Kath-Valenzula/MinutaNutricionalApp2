package com.example.minutanutricionalapp2.model

// Nutrientes por receta 
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