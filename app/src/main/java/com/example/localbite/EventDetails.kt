package com.example.localbite

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.data.model.Event
import com.example.localbite.data.model.UserList
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserListRepo
import com.example.localbite.data.repository.UserRepository
import com.example.localbite.ui.user_nav_fragments.CustomerViewModel
import com.example.localbite.ui.user_nav_fragments.CustomerViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.bumptech.glide.request.RequestOptions

class EventDetails : AppCompatActivity() {
    private val userRepository = UserRepository()
    private lateinit var viewModel: CustomerViewModel
    private val eventRepository = EventRepository()
    private val restaurantRepository = RestaurantRepository()
    private val userListRepo = UserListRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_event_details)

        val eventButton = findViewById<Button>(R.id.registerButton)

        eventButton.setOnClickListener {
            addUserToEvent()
        }

        startEventDetails()
    }

    private fun addUserToEvent() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        viewModel = ViewModelProvider(this, CustomerViewModelFactory(restaurantRepository, eventRepository, userRepository)).get(CustomerViewModel::class.java)

        if (userId != null) {
            viewModel.getUserById(userId) {user ->
                if (user != null) {
                    val eventID = intent.getStringExtra("id").toString()

                    val userList = UserList(
                        id = eventID,
                        newUser = user.name
                    )

                    userListRepo.addUserToEventList(userList) { success, message ->
                        if (success) {
                            println("Success: $message")
                        } else {
                            println("Error: $message")
                        }
                    }
                }
            }
        }


    }

    private fun startEventDetails() {
        // val restaurantName = findViewById<TextView>(R.id.restaurantName)
        textSetter("restaurantName", intent.getStringExtra("restaurantName").toString())
        textSetter("restaurantEventName", intent.getStringExtra("restaurantEventName").toString())
        textSetter("date", intent.getStringExtra("date").toString())
        textSetter("eventTime", intent.getStringExtra("eventTime").toString())
        textSetter("eventSummaryText", intent.getStringExtra("eventSummaryText").toString())
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