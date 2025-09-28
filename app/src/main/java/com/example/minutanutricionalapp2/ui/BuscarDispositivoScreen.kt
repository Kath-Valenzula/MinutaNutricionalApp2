package com.example.minutanutricionalapp2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarDispositivoScreen(navController: NavController) {
    var scanning by remember { mutableStateOf(false) }
    var results by remember { mutableStateOf(listOf("Pulsera Fit X", "Balanza Smart A1", "Reloj Salud Pro")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar dispositivo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            Modifier.padding(inner).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        scanning = true
                        results = listOf("Pulsera Fit X", "Balanza Smart A1", "Reloj Salud Pro")
                    }
                ) { Text(if (scanning) "Escaneando..." else "Iniciar búsqueda") }

                OutlinedButton(onClick = { scanning = false }) { Text("Detener") }
            }

            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Cercanos", style = MaterialTheme.typography.titleMedium)
                    results.forEach { name ->
                        ListItem(
                            headlineContent = { Text(name) },
                            leadingContent = { Icon(Icons.Default.Bluetooth, contentDescription = null) },
                            supportingContent = { Text("Demo • Tap para vincular") }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
