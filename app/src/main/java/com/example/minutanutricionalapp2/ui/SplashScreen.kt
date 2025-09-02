package com.example.minutanutricionalapp2.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.minutanutricionalapp2.R
import kotlinx.coroutines.delay
import android.media.MediaPlayer

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    // Instancia y liberación segura del reproductor
    val player = remember {
        MediaPlayer.create(context, R.raw.tema_pokemon).apply {
            isLooping = false
            setVolume(0.3f, 0.3f)
        }
    }
    DisposableEffect(Unit) {
        player.start()
        onDispose {
            try {
                if (player.isPlaying) player.stop()
            } catch (_: Exception) { }
            player.release()
        }
    }

    // Navega a Login tras 5s y cierra el Splash del back stack
    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(800),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .semantics { contentDescription = "Pantalla de bienvenida" },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kath_cl),
                contentDescription = "Logo KATH.cl",
                modifier = Modifier.size(160.dp).alpha(alpha)
            )
            Text(
                text = "bienvenido nuevamente a tu minuta diaria",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.9f).alpha(alpha)
            )
        }
    }
}
