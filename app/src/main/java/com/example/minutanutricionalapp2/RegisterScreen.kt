package com.example.minutanutricionalapp2

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutanutricionalapp2.data.firebase.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focus = LocalFocusManager.current

    fun isValidEmail(s: String) = s.contains("@") && s.contains(".")

    fun friendlyAuthError(e: Exception): String {
        Log.e("Register", "Firebase signUp error", e)
        return when (e) {
            is FirebaseAuthWeakPasswordException ->
                "La contraseña es muy débil (mínimo 6 caracteres)."
            is FirebaseAuthInvalidCredentialsException ->
                "El correo no tiene un formato válido."
            is FirebaseAuthUserCollisionException ->
                "Ese correo ya está registrado."
            is FirebaseAuthException -> {
                // Ej: ERROR_OPERATION_NOT_ALLOWED cuando Email/Password está deshabilitado en Firebase
                if (e.errorCode.equals("ERROR_OPERATION_NOT_ALLOWED", ignoreCase = true))
                    "El método Email/Password está deshabilitado en Firebase. Actívalo en Auth → Métodos de acceso."
                else
                    e.localizedMessage ?: "Error de autenticación (${e.errorCode})."
            }
            else -> e.localizedMessage ?: "Error inesperado al registrar."
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pass, onValueChange = { pass = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    focus.clearFocus()
                    scope.launch {
                        if (name.isBlank() || !isValidEmail(email) || pass.length < 6) {
                            snackbarHostState.showSnackbar("Completa nombre, correo válido y contraseña (≥6).")
                        } else {
                            try {
                                FirebaseAuthService.register(email, pass)
                                if (name.isNotBlank()) FirebaseAuthService.updateDisplayName(name)
                                snackbarHostState.showSnackbar("Cuenta creada. Ingresa con tus datos")
                                navController.popBackStack()
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar(friendlyAuthError(e))
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .semantics { contentDescription = "Crear cuenta" }
            ) { Text("Crear cuenta") }
        }
    }
}
