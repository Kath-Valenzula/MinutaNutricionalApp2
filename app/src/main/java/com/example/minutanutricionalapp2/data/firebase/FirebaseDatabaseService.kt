package com.example.minutanutricionalapp2.data.firebase

import com.example.minutanutricionalapp2.model.IntakeRecord
import com.example.minutanutricionalapp2.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FirebaseDatabaseService {
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private fun todayKey(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    // -------- INTAKES --------
    fun saveIntakeForToday(record: IntakeRecord) {
        val uid = auth.currentUser?.uid ?: return
        val date = todayKey()
        db.child("intakes").child(uid).child(date)
            .child(record.recipeId.toString())
            .setValue(record)
    }

    fun listenToday(onList: (List<IntakeRecord>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        val date = todayKey()
        db.child("intakes").child(uid).child(date)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull { it.getValue(IntakeRecord::class.java) }
                    onList(list)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun deleteIntakeForToday(recipeId: Int) {
        val uid = auth.currentUser?.uid ?: return
        val date = todayKey()
        db.child("intakes").child(uid).child(date).child(recipeId.toString()).removeValue()
    }

    // -------- USERS --------
    fun saveUser(uid: String, user: User) {
        db.child("users").child(uid).setValue(user)
    }

    suspend fun getUser(uid: String): User? {
        val snap = db.child("users").child(uid).get().await()
        return snap.getValue(User::class.java)
    }

    fun observeUser(uid: String, onUser: (User?) -> Unit): () -> Unit {
        val ref = db.child("users").child(uid)
        val l = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onUser(snapshot.getValue(User::class.java))
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        ref.addValueEventListener(l)
        return { ref.removeEventListener(l) }
    }
}
