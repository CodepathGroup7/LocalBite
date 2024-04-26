package com.example.localbite.ui.nav_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.R
import com.example.localbite.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_accountpage, container, false)

        val logoutCard = view.findViewById<CardView>(R.id.logout_card_view)
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
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }


}