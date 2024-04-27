package com.example.localbite.ui.user_nav_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.R
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: CustomerViewModel
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var eventRepository: EventRepository
    private lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)
        //eventRecyclerView = view.findViewById(R.id.eventRec)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        eventRepository = EventRepository()
        restaurantRepository = RestaurantRepository()
        userRepository = UserRepository()
        viewModel = ViewModelProvider(this, CustomerViewModelFactory(restaurantRepository, eventRepository, userRepository)).get(CustomerViewModel::class.java)
        return view
    }


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val lat = 47.68592899999999
        val long = -122.131098
        val location = LatLng(lat, long)
        val zoomLevel = 14f
        addMarkersFromFirebaseRestaurants()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))

        googleMap?.setOnMarkerClickListener { marker ->
            // Extract restaurant ID from the marker (assuming it's stored as a tag)
            val restaurantId = marker.tag as String?

            // Check if the restaurant ID is not null
            if (restaurantId != null) {
//                 Start RestaurantDetailsActivity, passing the restaurant ID as an extra
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.nav_fragment_frame_layout, RestaurantDetailsFragment.newInstance(restaurantId))
                transaction?.addToBackStack("RestaurantDetailsFragment")
                transaction?.commit()
            }

            // Return true to indicate that the listener has consumed the event
            true
        }
    }

    fun addMarkersFromFirebaseRestaurants() {
        // Call the getAllRestaurants method to retrieve restaurants from Firebase
        viewModel.getAllRestaurants { restaurants ->
            for (restaurant in restaurants) {
                val lat = restaurant.address.lat.toDouble() // Convert latitude String to Double
                val lng = restaurant.address.lng.toDouble() // Convert longitude String to Double
                val restaurantName = restaurant.name

                // Create a LatLng object with the extracted latitude and longitude
                val latLng = LatLng(lat, lng)

                // Create a MarkerOptions object and set its position and title
                val markerOptions = MarkerOptions().position(latLng).title(restaurantName)

                // Add marker to the map
                val marker = googleMap.addMarker(markerOptions)
                marker?.tag = restaurant.id
            }
        }
    }


}