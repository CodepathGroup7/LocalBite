package com.example.localbite.data.repository

import com.example.localbite.data.model.Event
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EventRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://localbite-d92b7-default-rtdb.firebaseio.com").reference

    fun addEvent(event: Event, onComplete: (Boolean, String) -> Unit) {
        val eventId = database.child("events").push().key ?: ""
        event.id = eventId
        database.child("events").child(eventId).setValue(event)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, eventId)
                } else {
                    onComplete(false, "Failed to add event")
                }
            }
    }
}