package com.example.localbite.ui.user_nav_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.EventAdapter
import com.example.localbite.OfferAdapter
import com.example.localbite.R
import com.example.localbite.data.repository.OfferRepository

class OfferFragment: Fragment() {
    private lateinit var offerRepository: OfferRepository
    private lateinit var offerRecyclerView: RecyclerView
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
        val view = inflater.inflate(R.layout.fragment_offer_list, container, false)
        offerRecyclerView = view.findViewById(R.id.restaurantOfferRec)
        offerRepository = OfferRepository()
        fillOffers()
        return view
    }

    private fun fillOffers() {
        offerRepository.getAllOffers { offers ->
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            offerRecyclerView.layoutManager = linearLayoutManager
            offerRecyclerView.adapter = OfferAdapter(offers)
        }
    }

    companion object {
        fun newInstance(): OfferFragment {
            return OfferFragment()
        }
    }
}