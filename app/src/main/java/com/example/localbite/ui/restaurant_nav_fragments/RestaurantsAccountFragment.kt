package com.example.localbite.ui.restaurant_nav_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.localbite.R
import com.example.localbite.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RestaurantsAccountFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
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
        val view = inflater.inflate(R.layout.fragment_restaurant_account, container, false)

        val logoutCard = view.findViewById<CardView>(R.id.restaurant_logout_cardview)
        auth = FirebaseAuth.getInstance()
        logoutCard.setOnClickListener {
            // Handle logout action
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }

        return view
    }


    companion object {
        fun newInstance(): RestaurantsAccountFragment {
            return RestaurantsAccountFragment()
        }
    }
}