package com.example.minutanutricionalapp2.data

import com.example.minutanutricionalapp2.model.Recipe

object RecipesRepository {

    private val base = listOf(
        Recipe(
            id = 1,
            title = "Bowl quinoa y verduras",
            day = "Lunes",
            calories = 420,
            tags = listOf("vegano","rápido","alto en fibra"),
            ingredients = listOf("Quinoa 1 taza","Zanahoria","Brócoli","Aceite oliva","Sal"),
            steps = listOf("Cocer quinoa","Saltear verduras","Mezclar y servir"),
            tips = "Prioriza verduras de hoja y bebe agua suficiente."
        ),
        Recipe(
            id = 2,
            title = "Lentejas con zapallo",
            day = "Martes",
            calories = 510,
            tags = listOf("vegano","económico"),
            ingredients = listOf("Lentejas 1 taza","Zapallo","Cebolla","Comino"),
            steps = listOf("Remojar lentejas","Cocer con zapallo","Sazonar"),
            tips = "Acompaña con ensalada fresca para sumar micronutrientes."
        ),
        Recipe(
            id = 3,
            title = "Tacos de porotos negros",
            day = "Miércoles",
            calories = 480,
            tags = listOf("vegano","proteico"),
            ingredients = listOf("Porotos negros","Tortillas","Tomate","Palta"),
            steps = listOf("Saltear porotos","Armar tacos","Servir"),
            tips = "Usa tortillas integrales para mayor saciedad."
        ),
        Recipe(
            id = 4,
            title = "Pasta integral con pesto",
            day = "Jueves",
            calories = 560,
            tags = listOf("vegano","energético"),
            ingredients = listOf("Pasta integral","Albahaca","Aceite oliva","Nueces"),
            steps = listOf("Cocer pasta","Procesar pesto","Mezclar"),
            tips = "Controla porción si buscas ≤500 kcal."
        ),
        Recipe(
            id = 5,
            title = "Garbanzos al curry",
            day = "Viernes",
            calories = 490,
            tags = listOf("vegano","alto en proteína"),
            ingredients = listOf("Garbanzos","Leche coco","Curry","Cebolla"),
            steps = listOf("Saltear base","Agregar garbanzos","Reducir"),
            tips = "Acompaña con arroz integral en porción pequeña."
        ),
        Recipe(
            id = 6,
            title = "Ensalada tibia de tofu",
            day = "Sábado",
            calories = 430,
            tags = listOf("vegano","bajo en calorías"),
            ingredients = listOf("Tofu","Mix hojas","Tomate cherry","Vinagreta"),
            steps = listOf("Dorar tofu","Mezclar con hojas","Aliñar"),
            tips = "Añade semillas para grasas saludables."
        ),
        Recipe(
            id = 7,
            title = "Porotos granados versión vegana",
            day = "Domingo",
            calories = 540,
            tags = listOf("vegano","tradicional"),
            ingredients = listOf("Porotos","Choclo","Zapallo","Albahaca"),
            steps = listOf("Cocer porotos","Incorporar verduras","Rectificar sazón"),
            tips = "Sirve con ensalada de tomates para frescor."
        )
    )

    fun all(): List<Recipe> = base

    fun byDay(day: String?): List<Recipe> = if (day.isNullOrBlank() || day == "Todos") base else base.filter { it.day == day }

    fun lowCalories(max: Int = 500): List<Recipe> = base.filter { it.calories <= max }
}
