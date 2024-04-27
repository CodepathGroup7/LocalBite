package com.example.localbite.ui.user_nav_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.EventAdapter
import com.example.localbite.R
import com.example.localbite.data.model.Restaurant
import com.example.localbite.data.repository.EventRepository


class RestaurantEventListFragment() : Fragment() {
    private lateinit var eventRecyclerView: RecyclerView
    private val eventRepository = EventRepository()
    private lateinit var restaurantName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillEvents()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_event_list, container, false)
        eventRecyclerView = view.findViewById(R.id.restaurantEventRec)
        return view
    }

    private fun fillEvents() {
        eventRepository.getAllEvents { events ->
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            eventRecyclerView.layoutManager = linearLayoutManager
            eventRecyclerView.adapter = EventAdapter(events)
        }
    }

    companion object {
        fun newInstance(): EventFragment {
            return EventFragment()
        }
    }


}