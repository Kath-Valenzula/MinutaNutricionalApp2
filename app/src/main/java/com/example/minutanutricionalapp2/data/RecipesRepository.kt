package com.example.minutanutricionalapp2.data

import com.example.minutanutricionalapp2.model.Recipe

object RecipesRepository {

    fun all(): List<Recipe> = listOf(
        Recipe(
            id = 1,
            title = "Bowl de quinoa y garbanzos",
            day = "Lunes",
            calories = 520,
            tags = listOf("alto en proteínas"),
            tips = "Quinoa + garbanzos + palta + tomates.",
            imageName = "quinoa_garbanzos_bowl"
        ),
        Recipe(
            id = 2,
            title = "Tacos de lentejas",
            day = "Martes",
            calories = 480,
            tags = listOf("alto en fibra"),
            tips = "Tortillas de maíz, lentejas especiadas y pico de gallo.",
            imageName = "tacos_lentejas"
        ),
        Recipe(
            id = 3,
            title = "Curry de verduras y tofu",
            day = "Miércoles",
            calories = 560,
            tags = listOf("sin gluten"),
            tips = "Leche de coco, curry suave, brócoli y tofu.",
            imageName = "curry_verduras_tofu"
        ),
        Recipe(
            id = 4,
            title = "Pasta integral con pesto vegano",
            day = "Jueves",
            calories = 610,
            tags = listOf("alto en energía"),
            tips = "Pesto de albahaca con nueces.",
            imageName = "pasta_pesto_vegano"
        ),
        Recipe(
            id = 5,
            title = "Ensalada tibia de porotos negros",
            day = "Viernes",
            calories = 450,
            tags = listOf("light"),
            tips = "Maíz, tomate, cilantro y limón.",
            imageName = "ensalada_porotos_negros"
        ),
        Recipe(
            id = 6,
            title = "Sopa cremosa de calabaza",
            day = "Sábado",
            calories = 430,
            tags = listOf("reconfortante"),
            tips = "Calabaza + leche vegetal suave.",
            imageName = "sopa_cremosa_de_calabaza"
        ),
        Recipe(
            id = 7,
            title = "Porotos granados versión vegana",
            day = "Domingo",
            calories = 540,
            tags = listOf("tradicional"),
            tips = "Porotos, zapallo, choclo y sofrito.",
            imageName = "porotos_granados_vegano"
        ),
        Recipe(
            id = 8,
            title = "Garbanzos al curry",
            day = "Viernes",
            calories = 490,
            tags = listOf("alto en proteína"),
            tips = "Garbanzos al curry con arroz.",
            imageName = "garbanzos_curry"
        ),
        Recipe(
            id = 9,
            title = "Lentejas con zapallo",
            day = "Martes",
            calories = 510,
            tags = listOf("económico"),
            tips = "Guiso de lentejas y zapallo.",
            imageName = "lentejas_zapallo"
        ),
    )

    fun byDay(day: String): List<Recipe> =
        if (day == "Todos") all() else all().filter { it.day == day }
}
