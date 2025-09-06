package com.example.minutanutricionalapp2

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
import com.example.minutanutricionalapp2.data.UserRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focus = LocalFocusManager.current

    fun isValidEmail(s: String) = s.contains("@") && s.contains(".")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Recuperar contraseña") },
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
                value = email, onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    focus.clearFocus()
                    scope.launch {
                        if (!isValidEmail(email)) {
                            snackbarHostState.showSnackbar("Ingresa un correo válido.")
                            return@launch
                        }
                        if (UserRepository.exists(email)) {
                            snackbarHostState.showSnackbar("Te enviamos un link para recuperar tu contraseña.")
                            navController.popBackStack()
                        } else {
                            snackbarHostState.showSnackbar("Correo no encontrado.")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .semantics { contentDescription = "Enviar link de recuperación" }
            ) {
                Text("Enviar link de recuperación")
            }
        }
    }
}
