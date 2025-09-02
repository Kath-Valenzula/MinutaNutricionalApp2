package com.example.minutanutricionalapp2.model

data class Nutrition(
    val calories: Int,
    val proteinG: Int,
    val carbsG: Int,
    val fatG: Int,
    val vitaminsMg: Map<String, Int> = emptyMap()
)

data class Totals(
    val calories: Int = 0,
    val proteinG: Int = 0,
    val carbsG: Int = 0,
    val fatG: Int = 0
) {
    operator fun plus(n: Nutrition) = Totals(
        calories + n.calories,
        proteinG + n.proteinG,
        carbsG + n.carbsG,
        fatG + n.fatG
    )
}
