package com.example.minutanutricionalapp2.data.firebase

import com.example.minutanutricionalapp2.model.IntakeRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FirebaseDatabaseService {
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { FirebaseAuth.getInstance() }

    fun saveIntakeForToday(record: IntakeRecord) {
        val uid = auth.currentUser?.uid ?: return
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        db.child("intakes")
            .child(uid)
            .child(date)
            .child(record.recipeId.toString())
            .setValue(record)
    }
}
