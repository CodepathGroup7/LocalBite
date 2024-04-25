package com.example.localbite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.localbite.data.model.User
import com.example.localbite.data.model.UserType
import com.example.localbite.ui.login.LoginActivity
import com.example.localbite.ui.nav_fragments.AccountFragment
import com.example.localbite.ui.restaurant_nav_fragments.RestaurantsAccountFragment
import com.example.localbite.ui.restaurant_nav_fragments.RestaurantsEventFragment
import com.example.localbite.ui.restaurant_nav_fragments.RestaurantsOffersFragment
import com.example.localbite.ui.user_nav_fragments.EventFragment
import com.example.localbite.ui.user_nav_fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        } else {
            val userId = currentUser.uid

            database.reference.child("users").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(User::class.java)
                            val userType = user?.userType
                            if (userType != null) {
                                // Use userType as needed (e.g., to determine user privileges)
                                // Example: Check userType and navigate accordingly
                                if (userType == UserType.CUSTOMER) {
                                    // User is a customer
                                    bottomNavigationView.menu.clear()
                                    bottomNavigationView.inflateMenu(R.menu.menu_nav_button)

                                    val homepageFragment: Fragment = HomeFragment()
                                    val eventFragment: Fragment = EventFragment()
                                    val accountFragment: Fragment = AccountFragment()


                                    bottomNavigationView.setOnItemSelectedListener { item ->
                                        lateinit var fragment: Fragment
                                        when (item.itemId) {
                                            R.id.homepage_nav_button -> fragment = homepageFragment
                                            R.id.event_list_nav_button -> fragment = eventFragment
                                            R.id.account_nav_button -> fragment = accountFragment
                                        }
                                        replaceFragment(fragment)
                                        true
                                    }

                                    bottomNavigationView.selectedItemId = R.id.homepage_nav_button

                                } else if (userType == UserType.RESTAURANT) {
                                    // User is a restaurant owner
                                    bottomNavigationView.menu.clear()
                                    bottomNavigationView.inflateMenu(R.menu.restaurant_menu_nav_bar)

                                    val restaurantsEventFragment: Fragment = RestaurantsEventFragment()
                                    val restaurantsOfferFragment: Fragment = RestaurantsOffersFragment()
                                    val restaurantsAccountFragment: Fragment = RestaurantsAccountFragment()

                                    bottomNavigationView.setOnItemSelectedListener { item ->
                                        lateinit var fragment: Fragment
                                        when (item.itemId) {
                                            R.id.restaurant_event_nav -> fragment = restaurantsEventFragment
                                            R.id.restaurant_offers_nav -> fragment = restaurantsOfferFragment
                                            R.id.restaurant_account_nav -> fragment = restaurantsAccountFragment
                                        }
                                        replaceFragment(fragment)
                                        true
                                    }

                                    bottomNavigationView.selectedItemId = R.id.restaurant_event_nav
                                }
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle database error
                        Log.e("Database Error", databaseError.message)
                    }
                })

        }
    }

    private fun replaceFragment(frag: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_fragment_frame_layout, frag)
        fragmentTransaction.commit()
    }


}