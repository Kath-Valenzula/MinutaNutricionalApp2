package com.example.minutanutricionalapp2.data.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    private val auth by lazy { Firebase.auth }

    fun currentUid(): String? = auth.currentUser?.uid
    fun signOut() = auth.signOut()

    suspend fun login(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
        Unit
    }

    suspend fun register(name: String, email: String, password: String): Result<Unit> = runCatching {
        auth.createUserWithEmailAndPassword(email, password).await()
      
        // auth.currentUser?.updateProfile(userProfileChangeRequest { displayName = name })?.await()
        Unit
    }

    suspend fun sendPasswordReset(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email).await()
        Unit
    }
}