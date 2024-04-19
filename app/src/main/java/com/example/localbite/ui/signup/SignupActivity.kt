package com.example.localbite.ui.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.localbite.R


class SignupActivity: AppCompatActivity() {
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
}