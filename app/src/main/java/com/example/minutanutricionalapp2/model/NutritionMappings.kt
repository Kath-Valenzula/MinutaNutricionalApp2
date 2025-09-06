package com.example.minutanutricionalapp2.model

import com.example.minutanutricionalapp2.data.NutritionTotals

fun Nutrition.toTotals(): NutritionTotals =
    NutritionTotals(
        calories = calories,
        proteinG = proteinG,
        carbsG = carbsG,
        fatG = fatG
    )
