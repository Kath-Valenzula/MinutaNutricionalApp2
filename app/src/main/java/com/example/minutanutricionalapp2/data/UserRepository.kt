package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.mutableStateListOf
import com.example.minutanutricionalapp2.model.User

object UserRepository {
    private val users = mutableStateListOf<User>()
    var current: User? = null
        private set

    init {
        if (users.isEmpty()) {
            users += listOf(
                User("Ana Vega", "ana@demo.cl", "Ana*123"),
                User("Bruno Díaz", "bruno@demo.cl", "Bruno*123"),
                User("Carla Soto", "carla@demo.cl", "Carla*123"),
                User("Diego Ruiz", "diego@demo.cl", "Diego*123"),
                User("Eva Pino", "eva@demo.cl", "Eva*123")
            )
        }
    }

    fun login(email: String, password: String): Boolean {
        val u = users.firstOrNull { it.email.equals(email.trim(), true) && it.password == password }
        current = u
        return u != null
    }

    fun exists(email: String) = users.any { it.email.equals(email.trim(), true) }

    fun register(name: String, email: String, password: String): Result<Unit> {
        val e = email.trim()
        if (e.isBlank() || name.isBlank() || password.length < 6) {
            return Result.failure(IllegalArgumentException("Datos inválidos"))
        }
        if (exists(e)) return Result.failure(IllegalStateException("El correo ya está registrado"))
        val u = User(name.trim(), e, password)
        users += u
        current = u
        return Result.success(Unit)
    }

    fun updateProfile(name: String, email: String): Result<Unit> {
        val u = current ?: return Result.failure(IllegalStateException("Sin sesión"))
        val e = email.trim()
        if (e.isBlank() || name.isBlank()) return Result.failure(IllegalArgumentException("Datos inválidos"))
        if (!u.email.equals(e, true) && exists(e)) return Result.failure(IllegalStateException("Correo ya usado"))
        u.name = name.trim()
        u.email = e
        return Result.success(Unit)
    }

    fun changePassword(old: String, new: String): Result<Unit> {
        val u = current ?: return Result.failure(IllegalStateException("Sin sesión"))
        if (u.password != old) return Result.failure(IllegalArgumentException("Contraseña actual incorrecta"))
        if (new.length < 6) return Result.failure(IllegalArgumentException("Nueva contraseña débil"))
        u.password = new
        return Result.success(Unit)
    }
}
