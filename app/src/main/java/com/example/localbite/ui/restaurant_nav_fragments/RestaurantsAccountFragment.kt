package com.example.localbite.ui.restaurant_nav_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.R
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.OfferRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.ui.login.LoginActivity
import com.example.localbite.ui.user_nav_fragments.CustomerViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

class RestaurantsAccountFragment: Fragment(), OnMapReadyCallback {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var eventRepository: EventRepository
    private lateinit var offerRepository: OfferRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantRepository = RestaurantRepository()
        eventRepository = EventRepository()
        offerRepository = OfferRepository()
        viewModel = ViewModelProvider(this, RestaurantViewModelFactory(eventRepository, offerRepository, restaurantRepository)).get(RestaurantViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_account, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.profile_restaurant_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val restaurantName = view.findViewById<TextView>(R.id.profile_restaurant_name)
        val restaurantAddress = view.findViewById<TextView>(R.id.profile_restaurant_address)
        val restaurantDescription = view.findViewById<TextView>(R.id.profile_restaurant_description)
        val restaurantImage = view.findViewById<ImageView>(R.id.profile_restaurant_image)
        val logoutCard = view.findViewById<CardView>(R.id.restaurant_logout_cardview)
        auth = FirebaseAuth.getInstance()
        logoutCard.setOnClickListener {
            // Handle logout action
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        if (userId != null) {
            viewModel.getRestaurantByUserId(userId) {restaurant ->
                restaurantName.text = restaurant?.name
                restaurantAddress.text = restaurant?.address?.address
                restaurantDescription.text = restaurant?.description
            }
        }

        return view
    }


    companion object {
        fun newInstance(): RestaurantsAccountFragment {
            return RestaurantsAccountFragment()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val lat = 47.68592899999999
        val long = -122.131098
        val location = LatLng(lat, long)
        val zoomLevel = 14f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))


        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        if (userId != null) {
            viewModel.getRestaurantByUserId(userId) { restaurant ->
                if (restaurant != null) {
                    val lat = restaurant.address.lat.toDouble() // Convert latitude String to Double
                    val lng = restaurant.address.lng.toDouble() // Convert longitude String to Double
                    val restaurantName = restaurant.name

                    // Create a LatLng object with the extracted latitude and longitude
                    val latLng = LatLng(lat, lng)

                    // Create a MarkerOptions object and set its position and title
                    val markerOptions = MarkerOptions().position(latLng).title(restaurantName)

                    // Add marker to the map
                    googleMap.addMarker(markerOptions)
                }
            }
        }
    }
}