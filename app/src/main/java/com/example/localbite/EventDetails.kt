package com.example.localbite

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.data.model.Event
import com.google.gson.Gson
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.bumptech.glide.request.RequestOptions

class EventDetails : AppCompatActivity() {
    private val gson = Gson()
    //val event: Event = gson.fromJson(intent.getStringExtra("eventJSON").toString(), Event::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_event_details)

        startEventDetails()
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