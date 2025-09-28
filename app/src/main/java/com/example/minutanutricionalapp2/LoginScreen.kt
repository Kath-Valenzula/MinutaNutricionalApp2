package com.example.minutanutricionalapp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutanutricionalapp2.data.firebase.FirebaseAuthService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var pass  by remember { mutableStateOf("") }
    var showHint by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focus = LocalFocusManager.current

    fun isValidEmail(s: String) = s.contains("@") && s.contains(".")

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Iniciar sesión") }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Icono de correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Icono de candado") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    focus.clearFocus()
                    scope.launch {
                        if (email.isNotBlank() && pass.isNotBlank() && isValidEmail(email)) {
                            try {
                                FirebaseAuthService.login(email, pass)
                                navController.navigate(Screen.Minuta.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            } catch (_: Exception) {
                                snackbarHostState.showSnackbar("Credenciales inválidas")
                                showHint = true
                            }
                        } else {
                            snackbarHostState.showSnackbar("Revisa correo y contraseña")
                            showHint = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .semantics { contentDescription = "Botón entrar" }
            ) { Text("Entrar") }

            TextButton(
                onClick = { navController.navigate(Screen.Recover.route) },
                modifier = Modifier
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .semantics { contentDescription = "Olvidé mi contraseña" }
            ) { Text("¿Olvidaste tu contraseña?") }

            OutlinedButton(
                onClick = { navController.navigate(Screen.Register.route) },
                modifier = Modifier
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .semantics { contentDescription = "Crear cuenta nueva" }
            ) { Text("Crear cuenta") }

            if (showHint) {
                AssistChip(
                    onClick = { showHint = false },
                    label = { Text("Completa correo y contraseña válidos") }
                )
            }
        }
    }
}
