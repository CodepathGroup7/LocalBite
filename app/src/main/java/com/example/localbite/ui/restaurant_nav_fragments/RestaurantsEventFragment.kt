package com.example.localbite.ui.restaurant_nav_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.EventAdapter
import com.example.localbite.R
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RestaurantsEventFragment: Fragment() {
    private var eventRepository = EventRepository()
    private lateinit var restaurantsRecyclerView: RecyclerView
    private var restaurantRepository = RestaurantRepository()
    private lateinit var adapter: EventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillEvents()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_events, container, false)
        val addEventBtn = view.findViewById<Button>(R.id.add_event_btn)
        restaurantsRecyclerView = view.findViewById<RecyclerView>(R.id.restaurants_events_rv)
        addEventBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val addEventFragment = AddEventForm()
            fragmentManager.beginTransaction()
                .replace(R.id.nav_fragment_frame_layout, addEventFragment)
                .addToBackStack("AddEventForm")
                .commit()
            adapter.notifyDataSetChanged()
        }
        return view
    }

    private fun fillEvents() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null) {
            restaurantRepository.getRestaurantByUserId(uid) {restaurant ->
                restaurant?.name?.let {
                    eventRepository.getAllEventsByRestaurantName(restaurantName = it) { events ->
                        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        restaurantsRecyclerView.layoutManager = linearLayoutManager
                        adapter = EventAdapter(events)
                        Log.i("Events", "fillEvents: ${events.size}")
                        restaurantsRecyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }
                }
            }
        }


    }

    companion object {
        fun newInstance(): RestaurantsEventFragment {
            return RestaurantsEventFragment()
        }
    }
}