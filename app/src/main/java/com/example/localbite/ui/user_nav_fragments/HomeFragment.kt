package com.example.localbite.ui.user_nav_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.localbite.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
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
        val zoomLevel = 10f
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in Redmond"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }

    private fun addMarkerToMap(name: String, address: String, location: LatLng) {
        val markerOptions = MarkerOptions()
            .position(location)
            .title(name)
            .snippet(address)

        googleMap.addMarker(markerOptions)
    }

}