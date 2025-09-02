package com.example.minutanutricionalapp2.data

import com.example.minutanutricionalapp2.model.Nutrition

object NutritionRepository {
    private val map = mapOf(
        1 to Nutrition(420, 16, 58, 12, mapOf("Vit C" to 30, "Hierro" to 3)),
        2 to Nutrition(510, 24, 70, 9, mapOf("Vit A" to 180, "Vit C" to 22)),
        3 to Nutrition(480, 22, 60, 10, mapOf("Vit C" to 18, "Hierro" to 4)),
        4 to Nutrition(560, 17, 78, 16, mapOf("Vit E" to 3, "Vit K" to 110)),
        5 to Nutrition(490, 21, 62, 14, mapOf("Vit B6" to 0, "Hierro" to 5)),
        6 to Nutrition(430, 25, 28, 18, mapOf("Calcio" to 220, "Vit K" to 90)),
        7 to Nutrition(540, 20, 80, 12, mapOf("Vit A" to 200, "Vit C" to 26))
    )

    fun get(recipeId: Int): Nutrition? = map[recipeId]
}
