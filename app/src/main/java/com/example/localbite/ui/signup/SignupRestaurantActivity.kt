package com.example.localbite.ui.signup

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.LocalBite
import com.example.localbite.MainActivity
import com.example.localbite.R
import com.example.localbite.data.model.Restaurant
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class SignupRestaurantActivity: Fragment() {
    private var imgUrl: String = ""
    private var address: Map<String, String> = emptyMap()
    private lateinit var viewModel: SignupViewModel
    private lateinit var userRepo: UserRepository
    private lateinit var restaurantRepo: RestaurantRepository
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup_restaurant, container, false)
        val addRestaurantBtn = view.findViewById<Button>(R.id.restaurantSignUpBtn)
        val imgBtn = view.findViewById<ImageButton>(R.id.addRestaurantImageBtn)

        userRepo = UserRepository()
        restaurantRepo = RestaurantRepository()
        viewModel = ViewModelProvider(this, SignupViewModelFactory(userRepo, restaurantRepo)).get(SignupViewModel::class.java)

        userId = arguments?.getString("userId") ?: ""

        imgBtn.setOnClickListener {
            openGallery()
        }
        addRestaurantBtn.setOnClickListener {
            createRestaurant(userId)
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)
        }


        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(
                requireContext(),
                getString(R.string.google_maps_api_key)
            )
        }

        // Initialize AutocompleteSupportFragment
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.places_autocomplete_fragment) as? AutocompleteSupportFragment?

        // Specify the types of place data to return
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG))

        // Set the desired mode and filter for autocomplete suggestions
        autocompleteFragment?.setHint("Search for a place")
        autocompleteFragment?.setActivityMode(AutocompleteActivityMode.OVERLAY)

        // Set up a PlaceSelectionListener to handle the selected place
        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
                address = mapOf(
                    "name" to place.name,
                    "address" to place.address,
                    "lat" to place.latLng?.latitude.toString(),
                    "lng" to place.latLng?.longitude.toString()
                )


            }

            override fun onError(status: Status) {
                // Handle the error
                Log.e("Autocomplete Error", "An error occurred: $status")
            }
        })

        return view
    }



    fun createRestaurant(userId: String) {
        val name = view?.findViewById<EditText>(R.id.signUpRestaurantName)
        val address = address
        val description = view?.findViewById<EditText>(R.id.signUpRestaurantDescription)

        if (name != null && address != null && description != null) {
            val restaurant = Restaurant(userId = userId, name = name.text.toString(), address = address, description = description.text.toString(), imageUrl = imgUrl)
            viewModel.signupRestaurant(restaurant) { success, restaurantId ->
                if (success) {
                    Log.i("Restaurant Created:", restaurantId)
                } else {
                    Toast.makeText(activity, "Signup Restaurant Failed!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private val PICK_IMAGE_REQUEST = 123 // Request code for image picker

    // Function to launch gallery
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle the result of image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            val imageUrl: String? = selectedImageUri?.toString()

            // Use imageUrl (selected image URI) as needed
            if (imageUrl != null) {
                // Handle the selected image URL (URI)
                Log.d("ImageURL", imageUrl)
                imgUrl = imageUrl
                // You can use this imageUrl to display the selected image or upload it
            }
        }
    }


    private val READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1

    // Function to request permissions
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(LocalBite.instance.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        }
    }

    // Override onRequestPermissionsResult to handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch gallery or perform desired action
                openGallery()
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(LocalBite.instance.applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(userId: String): SignupRestaurantActivity {
            val fragment = SignupRestaurantActivity()
            val args = Bundle()
            args.putString("userId", userId)
            fragment.arguments = args
            return fragment

        }
    }


}