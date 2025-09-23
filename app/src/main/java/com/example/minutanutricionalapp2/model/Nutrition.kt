package com.example.minutanutricionalapp2.model

/**
 * Datos de una receta para sumar al consumo diario.
 * - calories: kcal
 * - proteinG, carbsG, fatG: gramos de macronutrientes
 * - micros: mapa opcional de vitaminas/minerales (p. ej. "Vit C" -> 30)
 */
data class Nutrition(
    val calories: Int = 0,
    val proteinG: Int = 0,
    val carbsG: Int = 0,
    val fatG: Int = 0,
    val micros: Map<String, Int> = emptyMap()
)

data class NutritionTotals(
    val calories: Int = 0,
    val proteinG: Int = 0,
    val carbsG: Int = 0,
    val fatG: Int = 0
)

/** Conversión de una sola receta a totales (ignora micros para el cálculo). */
fun Nutrition.toTotals(): NutritionTotals =
    NutritionTotals(calories, proteinG, carbsG, fatG)

/** Suma de totales (para IntakeTracker). */
operator fun NutritionTotals.plus(other: NutritionTotals): NutritionTotals =
    NutritionTotals(
        calories = calories + other.calories,
        proteinG = proteinG + other.proteinG,
        carbsG   = carbsG   + other.carbsG,
        fatG     = fatG     + other.fatG
    )
