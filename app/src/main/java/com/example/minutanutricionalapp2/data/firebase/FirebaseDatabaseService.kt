package com.example.minutanutricionalapp2.data.firebase

import com.example.minutanutricionalapp2.model.NutritionTotals
import com.example.minutanutricionalapp2.util.todayKey
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class FirebaseIntake(
    val recipeId: String,
    val title: String,
    val calories: Int,
    val proteinG: Int,
    val carbsG: Int,
    val fatG: Int,
    val timestamp: Long = System.currentTimeMillis()
)

object FirebaseDatabaseService {
    private val rootRef by lazy { Firebase.database.reference }

    suspend fun addIntakeForToday(uid: String, recipeId: String, title: String, t: NutritionTotals): Result<Unit> = runCatching {
        val dateKey = todayKey()
        val intake = FirebaseIntake(
            recipeId = recipeId,
            title = title,
            calories = t.calories,
            proteinG = t.proteinG,
            carbsG = t.carbsG,
            fatG = t.fatG
        )
        rootRef.child("intakes").child(uid).child(dateKey).push().setValue(intake).await()
        Unit
    }
}