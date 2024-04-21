package com.example.localbite.data.repository


import com.example.localbite.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserRepository() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://localbite-d92b7-default-rtdb.firebaseio.com").getReference("users")

    fun addUser(user: User, password: String, onComplete: (Boolean, String) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    user.id = userId
                    database.child("users").child(userId).setValue(user)
                    onComplete(true, userId)
                } else {
                    onComplete(false, task.exception?.message ?: "User registration failed")
                }
            }
    }

    fun getUserById(userId: String, onComplete: (User?) -> Unit) {
        database.child("users").child(userId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                onComplete(user)
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(null)
            }
        })
    }

    // Function to authenticate user with email and password
    fun loginUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    onComplete(true, userId)
                } else {
                    onComplete(false, null)
                }
            }
    }
}