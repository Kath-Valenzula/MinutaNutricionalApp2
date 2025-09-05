package com.example.minutanutricionalapp2.data

import com.example.minutanutricionalapp2.model.Recipe

object RecipesRepository {
    private val items = listOf(
        Recipe(1,"Bowl de quinoa y garbanzos","Lunes",520,listOf("alto en proteínas"),"Quinoa + garbanzos + palta + tomates.","rec_quinoa_garbanzos"),
        Recipe(2,"Tacos de lentejas","Martes",480,listOf("alto en fibra"),"Lentejas especiadas con pico de gallo.","rec_tacos_lentejas"),
        Recipe(3,"Curry de verduras y tofu","Miércoles",560,listOf("sin gluten"),"Curry suave con brócoli y tofu.","rec_curry_verduras"),
        Recipe(4,"Pasta integral con pesto vegano","Jueves",610,listOf("alto en energía"),"Pesto de albahaca con nueces.","rec_pasta_pesto_vegano"),
        Recipe(5,"Ensalada tibia de porotos negros","Viernes",450,listOf("light"),"Maíz, tomate, cilantro y limón.","rec_ensalada_porotos"),
        Recipe(6,"Sopa cremosa de calabaza","Sábado",430,listOf("reconfortante"),"Calabaza + leche vegetal suave.","rec_sopa_calabaza"),
        Recipe(7,"Burrito de tempeh","Domingo",540,listOf("alta saciedad"),"Tempeh marinado, arroz y vegetales.","rec_burrito_tempeh")
    )

    fun all() = items
    fun byDay(day: String): List<Recipe> = if (day == "Todos") items else items.filter { it.day == day }
}
