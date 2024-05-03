package com.example.localbite

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.data.model.Event
import com.example.localbite.data.model.User
import com.example.localbite.data.model.UserType
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.UserRepository
import com.example.localbite.ui.user_nav_fragments.EventFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.bumptech.glide.request.RequestOptions

class EventDetails() : AppCompatActivity() {
    private val gson = Gson()
    private val userRepository = UserRepository()
    private val eventRepository = EventRepository()
    private lateinit var eventId: String
    private lateinit var event: Event
    private lateinit var user: User
    private lateinit var membersList: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_event_details)
        val rsvpButton = findViewById<Button>(R.id.rsvp_btn)
        val memberText = findViewById<TextView>(R.id.members_list_title_text)
        val membersRV = findViewById<RecyclerView>(R.id.members_rv)
        eventId = intent.getStringExtra("id").toString()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userId = user?.uid

        if (userId != null) {
            userRepository.getUserById(userId) { user ->
                if (user != null) {
                    this.user = user
                    if(user.userType == UserType.RESTAURANT) {
                        memberText.visibility = View.VISIBLE
                        membersRV.visibility = View.VISIBLE
                        rsvpButton.visibility = View.GONE
                    } else {
                        memberText.visibility = View.GONE
                        membersRV.visibility = View.GONE
                        rsvpButton.visibility = View.VISIBLE
                    }
                }
            }
        }


        eventRepository.getEventById(eventId) { event ->
            if (event != null) {
                this.event = event
                membersList = event.participantList
                membersRV.adapter = EventDetailsAdapter(membersList)
                membersRV.layoutManager = LinearLayoutManager(this)
                startEventDetails()
                rsvpButton.setOnClickListener {

                    Log.i("User ID", "User ID: $userId")
                    if (userId != null) {
                        userRepository.getUserById(userId) { user ->
                            if (user != null) {
                                this.user = user
                                registerToEvent()

                            }
                        }
                    }
                }
            }
        }




    }

    private fun registerToEvent() {

        if (!event.participantList.contains(user.name)) {
            val updatedParticipantList = event.participantList.toMutableList()
            updatedParticipantList.add(user.name)
            event.participantList = updatedParticipantList.toMutableList()
        }

        Log.i("Participant List", "Participant List: ${event.participantList}")
        eventRepository.updateParticipantList(event.id, event.participantList) {success ->
            if (success) {
                Toast.makeText(this, "RSVP Successful", Toast.LENGTH_SHORT).show()
            }
        }
        val backIntent = Intent(this, MainActivity::class.java)
        startActivity(backIntent)
        finish()
    }

    private fun startEventDetails() {
        // val restaurantName = findViewById<TextView>(R.id.restaurantName)

        textSetter("restaurantName", event.restaurantName)
        textSetter("restaurantEventName", event.eventName)
        textSetter("date", event.eventDate)
        textSetter("eventTime", event.eventTime)
        textSetter("eventSummaryText", event.eventSummary)


    }

    private fun textSetter(textViewName: String, value: String) {
        val textViewId = resources.getIdentifier(textViewName, "id", packageName)
        if (textViewId != 0) {
            val textView = findViewById<TextView>(textViewId)
            if(value.isEmpty()) {
                textView.text = ""
            } else {
                textView.text = value
            }
        }
    }
}