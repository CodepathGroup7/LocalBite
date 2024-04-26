package com.example.localbite.ui.restaurant_nav_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.localbite.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddOfferForm: Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var offersRef: DatabaseReference
    private lateinit var offerName: String
    private lateinit var offerDescription: String
    private lateinit var offerStartDate: String
    private lateinit var offerEndDate: String
    private lateinit var offerAmount: String
    private lateinit var offerCoupunCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_offer_form, container, false)

        return view
    }
}