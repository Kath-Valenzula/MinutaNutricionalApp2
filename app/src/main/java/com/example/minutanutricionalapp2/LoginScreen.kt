package com.example.minutanutricionalapp2

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Iniciar sesión") }) }) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Correo") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pass, onValueChange = { pass = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (email.isNotBlank() && pass.isNotBlank()) {
                        navController.navigate(Screen.Minuta.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else showError = true
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Entrar") }

            TextButton(onClick = { navController.navigate(Screen.Recover.route) }) {
                Text("¿Olvidaste tu contraseña?")
            }
            OutlinedButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text("Crear cuenta")
            }

            if (showError) {
                AssistChip(onClick = { showError = false }, label = { Text("Completa correo y contraseña") })
            }
        }
    }
}
