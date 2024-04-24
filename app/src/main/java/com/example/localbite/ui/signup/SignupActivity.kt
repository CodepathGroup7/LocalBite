package com.example.localbite.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.localbite.R
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity


class SignupActivity: AppCompatActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        showFragment(SignupUserActivity())
    }

    private fun showFragment(fragment: Fragment) {
        currentFragment = fragment
        // Replace the current fragment with the new fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            currentFragment?.let { remove(it) }
            add(R.id.signUpContainer, fragment)
            commit()
        }
        currentFragment = fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    // Handle the selected place
                    val placeName = place.name
                    val placeAddress = place.address
                    // Use the place details as needed
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    // Handle error (e.g., display error message)
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation
                }
            }
        }
    }
}