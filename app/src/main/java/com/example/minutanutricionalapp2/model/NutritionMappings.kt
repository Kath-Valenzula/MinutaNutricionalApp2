package com.example.minutanutricionalapp2.model

fun Nutrition.toTotals(): NutritionTotals =
    NutritionTotals(calories, proteinG, carbsG, fatG)
