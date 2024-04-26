package com.example.localbite.ui.user_nav_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.localbite.R
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class RestaurantDetailsFragment(private val restaurantId: String): Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: CustomerViewModel
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var eventRepository: EventRepository
    private lateinit var fragmentPageAdapter: RestaurantDetailsFragmentPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantRepository = RestaurantRepository()
        eventRepository = EventRepository()
        viewModel = CustomerViewModel(restaurantRepository, eventRepository)
        fragmentPageAdapter = RestaurantDetailsFragmentPageAdapter(requireActivity().supportFragmentManager, lifecycle)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_details, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.details_restaurant_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val restaurantName = view.findViewById<TextView>(R.id.details_restaurant_name)
        val restaurantAddress = view.findViewById<TextView>(R.id.details_restaurant_address)
        val restaurantDescription = view.findViewById<TextView>(R.id.details_restaurant_description)
        val restaurantImage = view.findViewById<ImageView>(R.id.details_restaurant_image)
        val tabLayout = view.findViewById<TabLayout>(R.id.details_tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.details_view_pager)

        tabLayout.addTab(tabLayout.newTab().setText("Events"))
        tabLayout.addTab(tabLayout.newTab().setText("Offers"))
        viewPager.adapter = fragmentPageAdapter

        viewModel.getRestaurantById(restaurantId) {restaurant ->
            restaurantName.text = restaurant?.name
            restaurantAddress.text = restaurant?.address?.address
            restaurantDescription.text = restaurant?.description
        }

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })


        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val lat = 47.68592899999999
        val long = -122.131098
        val location = LatLng(lat, long)
        val zoomLevel = 14f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))

        viewModel.getRestaurantById(restaurantId) { restaurant ->
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

    companion object {
        fun newInstance(restaurantId: String): RestaurantDetailsFragment {
            return RestaurantDetailsFragment(restaurantId)
        }
    }
}