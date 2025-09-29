package com.example.minutanutricionalapp2.data.firebase

import com.example.minutanutricionalapp2.model.IntakeRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FirebaseDatabaseService {
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private fun todayKey(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    fun saveIntakeForToday(record: IntakeRecord) {
        val uid = auth.currentUser?.uid ?: return
        val date = todayKey()
        db.child("intakes")
            .child(uid)
            .child(date)
            .child(record.recipeId.toString())
            .setValue(record)
    }

    // READ 
    fun listenToday(onList: (List<IntakeRecord>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        val date = todayKey()
        db.child("intakes")
            .child(uid)
            .child(date)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull { it.getValue(IntakeRecord::class.java) }
                    onList(list)
                }
                override fun onCancelled(error: DatabaseError) { /* no-op */ }
            })
    }

    // DELETE 
    fun deleteIntakeForToday(recipeId: Int) {
        val uid = auth.currentUser?.uid ?: return
        val date = todayKey()
        db.child("intakes").child(uid).child(date).child(recipeId.toString()).removeValue()
    }
}
