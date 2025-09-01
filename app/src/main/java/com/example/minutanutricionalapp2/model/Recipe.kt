package com.example.minutanutricionalapp2.model

data class Recipe(
    val id: Int,
    val title: String,
    val day: String,
    val calories: Int,
    val tags: List<String>,
    val ingredients: List<String>,
    val steps: List<String>,
    val tips: String
)
