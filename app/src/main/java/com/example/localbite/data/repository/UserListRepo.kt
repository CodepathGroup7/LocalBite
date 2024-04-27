package com.example.localbite.data.repository

import com.example.localbite.data.model.Event
import com.example.localbite.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.localbite.data.model.UserList

class UserListRepo() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://localbite-d92b7-default-rtdb.firebaseio.com").reference

    fun addUserToEventList(userList: UserList, onComplete: (Boolean, String) -> Unit) {
//        val eventId = database.child("userlist").push().key ?: ""
//        userList.id = eventId

        database.child("userlist").child("eventId").setValue(userList)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "It worked")
                } else {
                    onComplete(false, "Failed to add event")
                }
            }

    }


}