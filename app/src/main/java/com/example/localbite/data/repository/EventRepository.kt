package com.example.localbite.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.localbite.data.model.Event
import com.example.localbite.data.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    fun addUserToEvent(eventID: String, newUser: String, onComplete: (Boolean, String) -> Unit) {
        val eventRef = database.child("events").child(eventID)

        val newParticipantRef = eventRef.child("participantList").push()
        newParticipantRef.setValue(newUser)
            .addOnCompleteListener { task -> // Set the new participant data
                if (task.isSuccessful) {
                    onComplete(true, "Participant added successfully")
                } else {
                    onComplete(false, "Failed to add participant: ${task.exception}")
                }
            }
    }



    fun getEventById(id: String, callback: (Event?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val eventsRef = database.getReference("events")

        eventsRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val event = snapshot.getValue(Event::class.java)
                callback(event)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Event Repository", "Error getting event by ID: $error")
                callback(null)
            }
        })
    }

    // Function to get all events
    fun getAllEvents(callback: (List<Event>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val eventsRef = database.getReference("events")

        eventsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = mutableListOf<Event>()
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(Event::class.java)
                    event?.let { events.add(it) }
                }
                callback(events)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Event Repository", "Error getting all events: $error")
                callback(emptyList())
            }
        })
    }

    fun getAllEventsByRestaurantName(restaurantName: String, callback: (List<Event>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val eventsRef = database.getReference("events")

        eventsRef.orderByChild("restaurantName").equalTo(restaurantName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = mutableListOf<Event>()
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(Event::class.java)
                    event?.let { events.add(it) }
                }
                callback(events)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Event Repository", "Error getting all events: $error")
                callback(emptyList())
            }
        })

    }
}