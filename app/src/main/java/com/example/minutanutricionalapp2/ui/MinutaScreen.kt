@file:Suppress("SpellCheckingInspection")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.minutanutricionalapp2.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.core.net.toUri
import com.example.minutanutricionalapp2.R
import com.example.minutanutricionalapp2.data.CalorieGoalStore
import com.example.minutanutricionalapp2.data.IntakeTracker
import com.example.minutanutricionalapp2.data.NutritionRepository
import com.example.minutanutricionalapp2.data.RecipesRepository
import com.example.minutanutricionalapp2.data.firebase.FirebaseDatabaseService
import com.example.minutanutricionalapp2.model.IntakeRecord
import com.example.minutanutricionalapp2.model.NutritionTotals
import com.example.minutanutricionalapp2.model.Recipe
import com.example.minutanutricionalapp2.model.toTotals
import com.example.minutanutricionalapp2.util.NetworkBanner
import com.example.minutanutricionalapp2.util.drawableIdByName
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

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

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showGoalDialog by remember { mutableStateOf(CalorieGoalStore.goalKcal <= 0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minuta semanal") },
                actions = {
                    IconButton(onClick = { showGoalDialog = true }) {
                        Icon(Icons.Default.RamenDining, contentDescription = "Cambiar meta")
                    }
                    IconButton(onClick = { nav.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Opciones")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .semantics { contentDescription = "Pantalla Minuta semanal con filtros y grilla" },
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NetworkBanner()

            val safeTotals = runCatching { IntakeTracker.totals }.getOrElse { NutritionTotals() }
            NutritionOverview(totals = safeTotals)

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
                columns = GridCells.Adaptive(minSize = 220.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered, key = { it.id }) { r ->
                    RecipeCard(
                        recipe = r,
                        onOpen = {
                            val encodedTitle = r.title.toUri().toString()
                            val encodedTips  = r.tips.toUri().toString()
                            nav.navigate("detail/$encodedTitle/$encodedTips")
                        },
                        onAdd = {
                            NutritionRepository.get(r.id)?.let { n ->
                                val t = n.toTotals()
                                IntakeTracker.add(t)
                                scope.launch { snackbar.showSnackbar("Agregado: ${r.title}") }
                                FirebaseDatabaseService.saveIntakeForToday(
                                    IntakeRecord(
                                        recipeId = r.id,
                                        title = r.title,
                                        calories = t.calories,
                                        proteinG = t.proteinG,
                                        carbsG = t.carbsG,
                                        fatG = t.fatG
                                    )
                                )
                            }
                        }
                    )
                }
            }

            TextButton(
                onClick = { showStats = !showStats },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) { Text(if (showStats) "Ocultar estadísticas" else "Mostrar estadísticas (Kotlin)") }

            AnimatedVisibility(visible = showStats) {
                KotlinStatsBlock(recipes = filtered)
            }
        }
    }

    if (showGoalDialog) {
        GoalDialog(
            initial = if (CalorieGoalStore.goalKcal > 0) CalorieGoalStore.goalKcal.toString() else "",
            onDismiss = { showGoalDialog = false },
            onSave = { kcal ->
                CalorieGoalStore.setGoal(kcal)
                showGoalDialog = false
            }
        )
    }
}

@Composable
private fun GoalDialog(
    initial: String,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var text by remember { mutableStateOf(initial) }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Meta diaria de calorías") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        error = null
                    },
                    label = { Text("kcal por día") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = error != null,
                    supportingText = { if (error != null) Text(error!!) }
                )
                Text("Ejemplo: 2000. Puedes cambiarla luego desde el ícono del bowl.")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val v = text.filter { it.isDigit() }.toIntOrNull() ?: -1
                if (v <= 0) {
                    error = "Ingresa un número válido mayor a 0"
                } else {
                    onSave(v)
                }
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun NutritionOverview(totals: NutritionTotals) {
    val goal = CalorieGoalStore.goalKcal
    val consumed = totals.calories
    val left = max(0, goal - consumed)
    val progress = if (goal > 0) min(1f, consumed.toFloat() / goal) else 0f

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(Modifier.size(96.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { 1f },
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
                    strokeWidth = 10.dp
                )
                CircularProgressIndicator(
                    progress = { progress },
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0f),
                    strokeWidth = 10.dp
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(consumed.toString(), style = MaterialTheme.typography.titleMedium)
                    Text("kcal", style = MaterialTheme.typography.labelSmall)
                }
            }
            Column(Modifier.weight(1f)) {
                Text("Resumen de hoy", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Meta: ${goal.coerceAtLeast(0)}")
                    Text("Restante: $left")
                }
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    StatMini("Prot (g)", totals.proteinG)
                    StatMini("Carbs (g)", totals.carbsG)
                    StatMini("Grasa (g)", totals.fatG)
                }
            }
        }
    }
}

@Composable
private fun StatMini(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text("$value", style = MaterialTheme.typography.bodyMedium)
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
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = selectedDay,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Día") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    days.forEach { d ->
                        DropdownMenuItem(text = { Text(d) }, onClick = { onDayChange(d); expanded = false })
                    }
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Checkbox(checked = onlyLow, onCheckedChange = onOnlyLowChange)
            Text("≤ 500 kcal")
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            RadioButton(selected = sortAsc, onClick = { onSortChange(true) }); Text("Calorías ↑")
            RadioButton(selected = !sortAsc, onClick = { onSortChange(false) }); Text("Calorías ↓")
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe, onOpen: () -> Unit, onAdd: () -> Unit) {
    val ctx = LocalContext.current
    val resId = drawableIdByName(ctx, recipe.imageName, recipe.title)

    ElevatedCard(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 220.dp)
            .semantics { contentDescription = "Receta ${recipe.title} ${recipe.calories} kcal" }
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val painter = if (resId != 0) painterResource(resId) else painterResource(R.drawable.ic_food_placeholder)
            Image(
                painter = painter,
                contentDescription = "Foto de ${recipe.title}",
                modifier = Modifier.fillMaxWidth().height(120.dp),
                contentScale = ContentScale.Crop
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.RamenDining, contentDescription = null)
                Text(recipe.title, style = MaterialTheme.typography.titleMedium)
            }
            Text("${recipe.day} • ${recipe.calories} kcal", style = MaterialTheme.typography.bodyMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                recipe.tags.take(3).forEach { tag -> AssistChip(onClick = {}, label = { Text(tag) }) }
            }
            Spacer(Modifier.weight(1f, fill = true))
            Button(
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Agregar a mi día", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            OutlinedButton(
                onClick = onOpen,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text("Ver receta completa", maxLines = 1, overflow = TextOverflow.Ellipsis)
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
