@file:Suppress("unused")
package com.example.minutanutricionalapp2.data

import com.example.minutanutricionalapp2.data.firebase.FirebaseAuthService
import com.example.minutanutricionalapp2.data.firebase.FirebaseDatabaseService
import com.example.minutanutricionalapp2.model.User

object UserRepository {

    // Registro en Auth y guardado de perfil en Realtime DB
    suspend fun register(name: String, email: String, pass: String): Result<Unit> = runCatching {
        val uid = FirebaseAuthService.register(email, pass)
        if (name.isNotBlank()) FirebaseAuthService.updateDisplayName(name)
        FirebaseDatabaseService.saveUser(uid, User(uid = uid, name = name, email = email))
    }

    // Login 
    suspend fun login(email: String, password: String): Result<Unit> =
        runCatching { FirebaseAuthService.login(email, password) }

    // Usuario actual 
    fun currentUser(): User? {
        val uid = FirebaseAuthService.currentUid() ?: return null
        return User(uid = uid)
    }
}
