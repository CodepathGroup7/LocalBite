package com.example.localbite.ui.restaurant_nav_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.localbite.R

class RestaurantsOffersFragment: Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_restaurant_offers, container, false)

        val addFormBtn = view.findViewById<Button>(R.id.add_offer_btn)

        addFormBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val addOfferFragment = AddOfferForm()
            fragmentManager.beginTransaction()
                .replace(R.id.nav_fragment_frame_layout, addOfferFragment)
                .addToBackStack("AddOfferForm")
                .commit()

        }
        return view
    }

    companion object {
        fun newInstance(): RestaurantsOffersFragment {
            return RestaurantsOffersFragment()
        }
    }
}