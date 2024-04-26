package com.example.localbite.ui.restaurant_nav_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.localbite.R
import com.example.localbite.data.model.Event
import com.example.localbite.data.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddEventForm: Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var eventsRef: DatabaseReference
    private lateinit var eventNameEditText: EditText
    private lateinit var eventDescriptionEditText: EditText
    private lateinit var eventTimeEditText: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var restaurantName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        eventsRef = database.reference.child("events")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_event_form, container, false)
        eventNameEditText = view.findViewById(R.id.event_name_edit_text)
        eventDescriptionEditText = view.findViewById(R.id.event_description_edit_text)
        eventTimeEditText = view.findViewById(R.id.event_time_picker)
        datePicker = view.findViewById(R.id.event_date_picker)
        val createEventBtn = view.findViewById<Button>(R.id.event_create_button)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).addListenerForSingleValueEvent(object:
                ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val restaurant = snapshot.getValue(Restaurant::class.java)
                        if (restaurant != null) {
                            restaurantName = restaurant.name
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Database error", error.message)
                    }

            })
        }
        createEventBtn.setOnClickListener {
            addRestaurantEvent()
        }

        return view
    }

    companion object {
        fun newInstance(): AddEventForm {
            return AddEventForm()
        }
    }

    private fun addRestaurantEvent() {

        val event = Event(
            eventName = eventNameEditText.text.toString(),
            restaurantName = restaurantName,
            eventSummary = eventDescriptionEditText.text.toString(),
            eventTime = eventTimeEditText.text.toString(),
            eventDate = "${datePicker.month}/${datePicker.dayOfMonth}/${datePicker.year}"
        )

        val eventId = eventsRef.push().key ?: return // Ensure a valid key is generated
        event.id = eventId
        // Set the event object under the generated key
        eventsRef.child(eventId).setValue(event)
            .addOnSuccessListener {
                // Event added successfully
                Toast.makeText(context, "Event added successfully", Toast.LENGTH_SHORT).show()
                val fragmentManager = requireActivity().supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0 ) {
                    fragmentManager.popBackStackImmediate()
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Toast.makeText(context, "Error Occurred: ${exception.message}", Toast.LENGTH_SHORT).show()

            }
    }
}