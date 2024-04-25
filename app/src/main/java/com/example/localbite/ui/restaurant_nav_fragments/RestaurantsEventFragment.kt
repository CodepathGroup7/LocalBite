package com.example.localbite.ui.restaurant_nav_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.localbite.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RestaurantsEventFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_events, container, false)
        val addEventBtn = view.findViewById<Button>(R.id.add_event_btn)

        addEventBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val addEventFragment = AddEventForm()
            fragmentManager.beginTransaction()
                .replace(R.id.nav_fragment_frame_layout, addEventFragment)
                .addToBackStack("AddEventForm")
                .commit()
        }
        return view
    }



    companion object {
        fun newInstance(): RestaurantsEventFragment {
            return RestaurantsEventFragment()
        }
    }
}