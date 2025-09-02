@file:Suppress("SpellCheckingInspection")
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.minutanutricionalapp2.ui

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutanutricionalapp2.data.RecipesRepository
import com.example.minutanutricionalapp2.model.Recipe

@Composable
fun MinutaScreen(nav: NavController) {
    var selectedDay by remember { mutableStateOf("Todos") }
    var onlyLow by remember { mutableStateOf(false) }
    var sortAsc by remember { mutableStateOf(true) }
    var showStats by remember { mutableStateOf(false) }

    val days = listOf("Todos","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo")

    val filtered = remember(selectedDay, onlyLow, sortAsc) {
        val base = RecipesRepository.byDay(selectedDay).let { list ->
            if (onlyLow) list.filter { it.calories <= 500 } else list
        }
        if (sortAsc) base.sortedBy { it.calories } else base.sortedByDescending { it.calories }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Minuta semanal") }) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .semantics { contentDescription = "Pantalla Minuta semanal con filtros y grilla" },
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterRow(
                days = days,
                selectedDay = selectedDay,
                onDayChange = { selectedDay = it },
                onlyLow = onlyLow,
                onOnlyLowChange = { onlyLow = it },
                sortAsc = sortAsc,
                onSortChange = { sortAsc = it }
            )

            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Adaptive(minSize = 160.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered, key = { it.id }) { r ->
                    RecipeCard(recipe = r, onOpen = {
                        val encodedTitle = Uri.encode(r.title)
                        val encodedTips = Uri.encode(r.tips)
                        nav.navigate("detail/$encodedTitle/$encodedTips")
                    })
                }
            }

            TextButton(
                onClick = { showStats = !showStats },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) { Text(if (showStats) "Ocultar estadísticas" else "Mostrar estadísticas (Kotlin)") }

            AnimatedVisibility(visible = showStats) {
                KotlinStatsBlock(recipes = filtered)
            }
        }
    }
}

@Composable
private fun FilterRow(
    days: List<String>,
    selectedDay: String,
    onDayChange: (String) -> Unit,
    onlyLow: Boolean,
    onOnlyLowChange: (Boolean) -> Unit,
    sortAsc: Boolean,
    onSortChange: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedDay,
                onValueChange = {},
                readOnly = true,
                label = { Text("Día") },
                modifier = Modifier.weight(1f)
            )
            AssistChip(onClick = { onDayChange(nextDay(days, selectedDay)) }, label = { Text("Cambiar día") })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterChip(selected = onlyLow, onClick = { onOnlyLowChange(!onlyLow) }, label = { Text("≤ 500 kcal") })
            FilterChip(selected = sortAsc, onClick = { onSortChange(!sortAsc) }, label = { Text(if (sortAsc) "Asc" else "Desc") })
        }
    }
}

private fun nextDay(days: List<String>, current: String): String {
    val idx = days.indexOf(current).takeIf { it >= 0 } ?: 0
    val next = (idx + 1) % days.size
    return days[next]
}

@Composable
private fun RecipeCard(recipe: Recipe, onOpen: () -> Unit) {
    ElevatedCard(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .semantics { contentDescription = "Receta ${recipe.title} ${recipe.calories} kcal" }
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(recipe.title, style = MaterialTheme.typography.titleMedium)
            Text("${recipe.day} • ${recipe.calories} kcal")
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                recipe.tags.take(3).forEach { tag -> AssistChip(onClick = {}, label = { Text(tag) }) }
            }
            Spacer(Modifier.weight(1f, fill = true))
            Button(onClick = onOpen, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Text("Ver receta completa")
            }
        }
    }
}

@Composable
private fun KotlinStatsBlock(recipes: List<Recipe>) {
    val total = recipes.fold(0) { acc, r -> acc + r.calories }
    val promedio = if (recipes.isNotEmpty()) total / recipes.size else 0
    val nivel = when {
        promedio <= 450 -> "Bajo"
        promedio in 451..520 -> "Medio"
        else -> "Alto"
    }

    var i = 0
    var cont = 0
    while (i < recipes.size) {
        val r = recipes[i]
        i++
        if (r.calories > 500) continue
        cont++
        if (cont >= 2) break
    }

    Column(Modifier.fillMaxWidth().semantics { contentDescription = "Estadísticas Kotlin" }) {
        Text("Total kcal visibles: $total")
        Text("Promedio por receta: $promedio ($nivel)")
        Text("Primeras 2 ≤500 kcal encontradas: $cont")
    }
}
