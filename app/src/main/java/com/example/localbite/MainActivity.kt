package com.example.localbite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.localbite.ui.nav_fragments.AccountFragment
import com.example.localbite.ui.user_nav_fragments.EventFragment
import com.example.localbite.ui.user_nav_fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val homepageFragment: Fragment = HomeFragment()
        val eventFragment: Fragment = EventFragment()
        val accountFragment: Fragment = AccountFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

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
    }

    private fun replaceFragment(frag: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_fragment_frame_layout, frag)
        fragmentTransaction.commit()
    }


}