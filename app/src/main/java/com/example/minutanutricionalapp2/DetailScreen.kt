package com.example.minutanutricionalapp2

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    title: String?,
    tips: String?
) {
    val decodedTitle = title?.let { Uri.decode(it) }.orEmpty()
    val decodedTips  = tips?.let { Uri.decode(it) }.orEmpty()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Receta") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.semantics { contentDescription = "Volver atrás" }
                    ) {
                        // contentDescription en el Icon va en null porque ya lo
                        // expusimos en el IconButton para lectores de pantalla
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { inner ->
        DetailContent(inner, decodedTitle, decodedTips)
    }
}

@Composable
private fun DetailContent(
    inner: PaddingValues,
    title: String,
    tips: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(inner)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title.ifBlank { "Sin título" },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Ingredientes (sugeridos):\n• Garbanzos cocidos\n• Palta\n• Tomate cherry\n• Quínoa\n• Limón y aceite de oliva",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Pasos:\n1) Mezcla los ingredientes.\n2) Aliña con limón y aceite.\n3) Sirve frío.",
            style = MaterialTheme.typography.bodyMedium
        )
        if (tips.isNotBlank()) {
            Text(
                text = "Tips nutricionales:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = tips, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
