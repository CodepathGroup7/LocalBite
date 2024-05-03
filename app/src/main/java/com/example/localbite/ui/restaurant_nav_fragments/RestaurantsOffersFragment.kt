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
import com.example.localbite.OfferAdapter
import com.example.localbite.R
import com.example.localbite.data.repository.OfferRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.google.firebase.auth.FirebaseAuth

class RestaurantsOffersFragment: Fragment() {
    private var offerRepository = OfferRepository()
    private lateinit var restaurantsRecyclerView: RecyclerView
    private var restaurantRepository = RestaurantRepository()
    private lateinit var adapter: OfferAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillOffers()
    }

    private fun fillOffers() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val uid = user?.uid
        if (uid != null) {
            restaurantRepository.getRestaurantByUserId(uid) { restaurant ->
                Log.i("restaurant", restaurant.toString())
                if (restaurant != null) {
                    offerRepository.getAllOffersByRestaurantId(restaurant.id) { offers ->
                        val linearLayoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        restaurantsRecyclerView.layoutManager = linearLayoutManager
                        adapter = OfferAdapter(offers)
                        restaurantsRecyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
//        offerRepository.getAllOffers { offers ->
//            val linearLayoutManager =
//                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            restaurantsRecyclerView.layoutManager = linearLayoutManager
//            adapter = OfferAdapter(offers)
//            restaurantsRecyclerView.adapter = adapter
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_offers, container, false)

        val addFormBtn = view.findViewById<Button>(R.id.add_offer_btn)
        restaurantsRecyclerView = view.findViewById(R.id.restaurants_offers_rv)

        addFormBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val addOfferFragment = AddOfferForm()
            fragmentManager.beginTransaction()
                .replace(R.id.nav_fragment_frame_layout, addOfferFragment)
                .addToBackStack("AddOfferForm")
                .commit()
            adapter.notifyDataSetChanged()

        }
        return view
    }

    companion object {
        fun newInstance(): RestaurantsOffersFragment {
            return RestaurantsOffersFragment()
        }
    }
}