package com.example.minutanutricionalapp2.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    suspend fun register(email: String, password: String): String {
        val res = auth.createUserWithEmailAndPassword(email, password).await()
        return res.user?.uid ?: throw IllegalStateException("UID nulo")
    }

    suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    suspend fun updateDisplayName(name: String) {
        val user = auth.currentUser ?: throw IllegalStateException("No autenticado")
        val req = userProfileChangeRequest { displayName = name }
        user.updateProfile(req).await()
    }

    suspend fun updateEmail(email: String) {
        val user = auth.currentUser ?: throw IllegalStateException("No autenticado")
        user.updateEmail(email).await()
    }

    suspend fun updatePassword(newPass: String) {
        val user = auth.currentUser ?: throw IllegalStateException("No autenticado")
        user.updatePassword(newPass).await()
    }

    fun currentUid(): String? = auth.currentUser?.uid
    fun currentEmail(): String? = auth.currentUser?.email
    fun currentName(): String? = auth.currentUser?.displayName

    fun signOut() { auth.signOut() }
}
