package com.example.minutanutricionalapp2

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// ---- Datos de ejemplo ----
data class Recipe(
    val title: String,
    val day: String,
    val calories: Int,
    val tags: List<String>,
    val notes: String
)

private val veganRecipes = listOf(
    Recipe("Bowl de quinoa y garbanzos", "Lunes", 520, listOf("alto en proteínas"), "Quinoa + garbanzos + palta + tomates."),
    Recipe("Tacos de lentejas", "Martes", 480, listOf("alto en fibra"), "Tortillas de maíz, lentejas especiadas y pico de gallo."),
    Recipe("Curry de verduras y tofu", "Miércoles", 560, listOf("sin gluten"), "Leche de coco, curry suave, brócoli y tofu."),
    Recipe("Pasta integral con pesto vegano", "Jueves", 610, listOf("alto en energía"), "Pesto de albahaca con nueces."),
    Recipe("Ensalada tibia de porotos negros", "Viernes", 450, listOf("light"), "Maíz, tomate, cilantro y limón.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(navController: NavController) {

    // Filtros
    var selectedDay by remember { mutableStateOf("Todos") }
    var onlyLowCal by remember { mutableStateOf(false) }
    var orderByCalsAsc by remember { mutableStateOf(true) }
    val days = listOf("Todos", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

    // Estado del combo/drowpdown
    var expanded by remember { mutableStateOf(false) }

    // Aplicar filtros/orden
    val filtered = veganRecipes
        .filter { selectedDay == "Todos" || it.day == selectedDay }
        .filter { if (onlyLowCal) it.calories <= 500 else true }
        .let { list -> if (orderByCalsAsc) list.sortedBy { it.calories } else list.sortedByDescending { it.calories } }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Minuta semanal (vegana)") }) }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --- Filtros superiores ---
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Combo Día (Material 3)
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedDay,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Día") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()        // correcto en M3 (sin parámetros)
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        days.forEach { d ->
                            DropdownMenuItem(
                                text = { Text(d) },
                                onClick = {
                                    selectedDay = d
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Check ≤ 500 kcal
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = onlyLowCal, onCheckedChange = { onlyLowCal = it })
                    Text("≤ 500 kcal")
                }
            }

            // Radio: Orden por calorías
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = orderByCalsAsc, onClick = { orderByCalsAsc = true })
                    Text("Calorías ↑")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = !orderByCalsAsc, onClick = { orderByCalsAsc = false })
                    Text("Calorías ↓")
                }
            }

            // --- Grilla de recetas ---
            LazyVerticalGrid(
                columns = GridCells.Adaptive(220.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered) { recipe ->
                    ElevatedCard {
                        Column(
                            Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                recipe.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "${recipe.day} • ${recipe.calories} kcal",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            AssistChip(onClick = {}, label = { Text(recipe.tags.joinToString()) })
                            Text(recipe.notes, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(6.dp))
                            Button(
                                onClick = {
                                    val encodedTitle = Uri.encode(recipe.title)
                                    val encodedTips = Uri.encode("Consejo: hidrátate bien y prioriza verduras de hoja.")
                                    navController.navigate("detail/$encodedTitle/$encodedTips")
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) { Text("Ver receta completa") }
                        }
                    }
                }
            }
        }
    }
}
