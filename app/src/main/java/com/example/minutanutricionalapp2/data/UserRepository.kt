package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.mutableStateListOf
import com.example.minutanutricionalapp2.model.User

object UserRepository {
    private val users = mutableStateListOf<User>()

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

    fun login(email: String, password: String): Boolean =
        users.any { it.email.equals(email.trim(), true) && it.password == password }

    fun exists(email: String): Boolean =
        users.any { it.email.equals(email.trim(), true) }

    fun register(name: String, email: String, password: String): Result<Unit> {
        val e = email.trim()
        if (e.isBlank() || name.isBlank() || password.length < 6) {
            return Result.failure(IllegalArgumentException("Datos inválidos"))
        }
        if (exists(e)) return Result.failure(IllegalStateException("El correo ya está registrado"))
        users += User(name.trim(), e, password)
        return Result.success(Unit)
    }
}
