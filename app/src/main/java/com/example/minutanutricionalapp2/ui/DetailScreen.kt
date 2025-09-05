package com.example.minutanutricionalapp2.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutanutricionalapp2.data.RecipesRepository
import com.example.minutanutricionalapp2.util.drawableIdByName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, title: String?, tips: String?) {
    val decodedTitle = title?.let { Uri.decode(it) }.orEmpty()
    val decodedTips  = tips?.let { Uri.decode(it) }.orEmpty()
    val recipe = RecipesRepository.all().firstOrNull { it.title == decodedTitle }
    val resId = drawableIdByName(recipe?.imageName)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Receta", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.semantics { contentDescription = "Volver atrás" }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier.fillMaxSize().padding(inner).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (resId != 0) {
                ElevatedCard {
                    Image(painter = painterResource(id = resId), contentDescription = "Foto de $decodedTitle", modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
                }
            }

            Text(text = if (decodedTitle.isBlank()) "Sin título" else decodedTitle, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            HorizontalDivider()

            Text(text = "Ingredientes (sugeridos):\n• Garbanzos cocidos\n• Palta\n• Tomate cherry\n• Quínoa\n• Limón y aceite de oliva", style = MaterialTheme.typography.bodyMedium)
            HorizontalDivider()
            Text(text = "Pasos:\n1) Mezcla los ingredientes.\n2) Aliña con limón y aceite.\n3) Sirve frío.", style = MaterialTheme.typography.bodyMedium)

            if (decodedTips.isNotBlank()) {
                HorizontalDivider()
                Text(text = "Tips nutricionales:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = decodedTips, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
