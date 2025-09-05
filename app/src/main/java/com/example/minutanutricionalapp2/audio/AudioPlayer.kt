package com.example.minutanutricionalapp2.audio

import android.content.Context
import android.media.MediaPlayer
import com.example.minutanutricionalapp2.R
import com.example.minutanutricionalapp2.data.SettingsRepository

object AudioPlayer {
    private var player: MediaPlayer? = null

    fun ensurePlayingLoop(context: Context) {
        val app = context.applicationContext
        if (player == null) {
            player = MediaPlayer.create(app, R.raw.tema_pokemon).apply { isLooping = true }
        }
        applyVolume()
        if (player?.isPlaying != true) player?.start()
    }

    fun applyVolume() {
        val v = if (SettingsRepository.muted) 0f else SettingsRepository.volume
        player?.setVolume(v, v)
    }

    fun pause() { player?.pause() }
    fun release() { try { player?.release() } catch (_: Exception) {} finally { player = null } }
}
