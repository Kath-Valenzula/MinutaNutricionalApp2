@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.minutanutricionalapp2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutanutricionalapp2.audio.AudioPlayer
import com.example.minutanutricionalapp2.data.SettingsRepository
import com.example.minutanutricionalapp2.data.firebase.FirebaseAuthService
import com.example.minutanutricionalapp2.data.firebase.FirebaseDatabaseService
import com.example.minutanutricionalapp2.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SettingsScreen(navController: NavController) {
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val auth = remember { FirebaseAuth.getInstance() }

    var name by remember { mutableStateOf(SettingsRepository.displayName) }
    var email by remember { mutableStateOf(SettingsRepository.displayEmail) }
    var oldPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val u = auth.currentUser
        if (u != null) {
            FirebaseDatabaseService.getUser(u.uid)?.let { user ->
                name = user.name
                email = user.email
                SettingsRepository.displayName = user.name
                SettingsRepository.displayEmail = user.email
            }
        }
    }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Opciones") }) },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
                .semantics { contentDescription = "Pantalla de opciones" },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Sonido", style = MaterialTheme.typography.titleMedium)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Switch(
                            checked = !SettingsRepository.muted,
                            onCheckedChange = {
                                SettingsRepository.muted = !it
                                AudioPlayer.applyVolume()
                            }
                        )
                        Text(if (SettingsRepository.muted) "Silenciado" else "Sonando")
                    }
                    Slider(
                        value = SettingsRepository.volume,
                        onValueChange = {
                            SettingsRepository.volume = it
                            AudioPlayer.applyVolume()
                        },
                        valueRange = 0f..1f
                    )
                }
            }

            Card {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Apariencia", style = MaterialTheme.typography.titleMedium)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Switch(
                            checked = SettingsRepository.darkMode,
                            onCheckedChange = { SettingsRepository.darkMode = it }
                        )
                        Text(if (SettingsRepository.darkMode) "Modo nocturno" else "Modo claro")
                    }
                }
            }

            Card {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Perfil", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        label = { Text("Nombre") }, singleLine = true, modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = email, onValueChange = { email = it },
                        label = { Text("Correo") }, singleLine = true, modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = oldPass, onValueChange = { oldPass = it },
                            label = { Text("Contraseña actual") },
                            singleLine = true, visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = newPass, onValueChange = { newPass = it },
                            label = { Text("Nueva contraseña") },
                            singleLine = true, visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                scope.launch {
                                    val u = auth.currentUser
                                    if (u == null) {
                                        snackbar.showSnackbar("No autenticado")
                                    } else {
                                        try {
                                            if (email.isNotBlank() && email != (u.email ?: "")) {
                                                u.updateEmail(email).await()
                                            }
                                            if (name.isNotBlank()) {
                                                // actualiza también el displayName en Auth
                                                FirebaseAuthService.updateDisplayName(name)
                                            }
                                            FirebaseDatabaseService.saveUser(
                                                u.uid, User(uid = u.uid, name = name, email = email)
                                            )
                                            SettingsRepository.displayName = name
                                            SettingsRepository.displayEmail = email
                                            snackbar.showSnackbar("Datos guardados")
                                        } catch (e: Exception) {
                                            snackbar.showSnackbar(e.localizedMessage ?: "Error al guardar")
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) { Text("Guardar perfil") }

                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    val u = auth.currentUser
                                    if (u == null) {
                                        snackbar.showSnackbar("No autenticado")
                                    } else if (newPass.length < 6) {
                                        snackbar.showSnackbar("Nueva contraseña muy corta")
                                    } else {
                                        try {
                                            u.updatePassword(newPass).await()
                                            oldPass = ""; newPass = ""
                                            snackbar.showSnackbar("Contraseña cambiada")
                                        } catch (e: Exception) {
                                            snackbar.showSnackbar(e.localizedMessage ?: "No se pudo cambiar")
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) { Text("Cambiar contraseña") }
                    }
                }
            }

            Spacer(Modifier.weight(1f))
            TextButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.End)) {
                Text("Volver")
            }
        }
    }
}
