package com.example.minutanutricionalapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.minutanutricionalapp2.audio.AudioPlayer
import com.example.minutanutricionalapp2.ui.theme.MinutaNutricionalApp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinutaNutricionalApp2Theme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(navController = navController)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        AudioPlayer.ensurePlayingLoop(this)
    }

    override fun onStop() {
        super.onStop()
        AudioPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        AudioPlayer.release()
    }
}
