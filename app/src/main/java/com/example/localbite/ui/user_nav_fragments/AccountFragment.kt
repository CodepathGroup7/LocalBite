package com.example.localbite.ui.nav_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.R
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository
import com.example.localbite.ui.login.LoginActivity
import com.example.localbite.ui.user_nav_fragments.CustomerViewModel
import com.example.localbite.ui.user_nav_fragments.CustomerViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {
    private lateinit var eventRepository: EventRepository
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: CustomerViewModel

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRepository = UserRepository()
        eventRepository = EventRepository()
        restaurantRepository = RestaurantRepository()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_accountpage, container, false)
        val userAccountName = view.findViewById<TextView>(R.id.user_account_name)
        val userAccountEmail = view.findViewById<TextView>(R.id.user_account_email)
        val userAccountPhone = view.findViewById<TextView>(R.id.user_account_phone)
        val logoutCard = view.findViewById<CardView>(R.id.logout_card_view)
        viewModel = ViewModelProvider(this, CustomerViewModelFactory(restaurantRepository, eventRepository, userRepository)).get(CustomerViewModel::class.java)

        auth = FirebaseAuth.getInstance()
        logoutCard.setOnClickListener {
            // Handle logout action
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            viewModel.getUserById(userId) {user ->
                if (user != null) {
                    userAccountName.text = user.name
                    userAccountEmail.text = user.email
                    userAccountPhone.text = user.phone
                }
            }
        }


        return view
    }


    companion object {
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }


}