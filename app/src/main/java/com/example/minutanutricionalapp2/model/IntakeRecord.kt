package com.example.minutanutricionalapp2.model

data class IntakeRecord(
    val recipeId: Int = 0,
    val title: String = "",
    val calories: Int = 0,
    val proteinG: Int = 0,
    val carbsG: Int = 0,
    val fatG: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
