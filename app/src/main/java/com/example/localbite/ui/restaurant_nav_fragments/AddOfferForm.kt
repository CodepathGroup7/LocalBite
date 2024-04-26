package com.example.localbite.ui.restaurant_nav_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.R
import com.example.localbite.data.model.Offer
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.OfferRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.google.firebase.auth.FirebaseAuth

class AddOfferForm: Fragment() {
    private lateinit var eventRepository: EventRepository
    private lateinit var offerRepository: OfferRepository
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var offerNameEditText: EditText
    private lateinit var offerDescriptionEditText: EditText
    private lateinit var offerStartDateEditText: EditText
    private lateinit var offerEndDateEditText: EditText
    private lateinit var offerAmountEditText: EditText
    private lateinit var offerCoupunCodeEditText: EditText
    private lateinit var restaurantId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventRepository = EventRepository()
        offerRepository = OfferRepository()
        restaurantRepository = RestaurantRepository()
        viewModel = ViewModelProvider(this, RestaurantViewModelFactory(eventRepository, offerRepository, restaurantRepository)).get(RestaurantViewModel::class.java)
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
        offerNameEditText = view.findViewById(R.id.offer_name_edit_text)
        offerDescriptionEditText = view.findViewById(R.id.offer_description_edit_text)
        offerStartDateEditText = view.findViewById(R.id.offer_start_date_picker)
        offerEndDateEditText = view.findViewById(R.id.offer_end_date_picker)
        offerAmountEditText = view.findViewById(R.id.offer_discount_amount_edit_text)
        offerCoupunCodeEditText = view.findViewById(R.id.offer_discount_coupon_code_edit_text)
        val createOfferBtn = view.findViewById<Button>(R.id.offer_create_button)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        if (userId != null) {
            viewModel.getRestaurantByUserId(userId) {restaurant ->
                if (restaurant != null) {
                    restaurantId = restaurant.id
                }
            }

        }

        createOfferBtn.setOnClickListener {
            addRestaurantOffer()
        }

        return view
    }

    private fun addRestaurantOffer() {
        val offer = Offer(
            title = offerNameEditText.text.toString(),
            description = offerDescriptionEditText.text.toString(),
            startDate = offerStartDateEditText.text.toString(),
            endDate = offerEndDateEditText.text.toString(),
            discountAmount = offerAmountEditText.text.toString(),
            couponCode = offerCoupunCodeEditText.text.toString(),
            restaurantId = restaurantId
        )

        viewModel.addOffer(offer) {success, offerId ->
            if (success) {
                // Offer added successfully
                Toast.makeText(context, "Offer added successfully", Toast.LENGTH_SHORT).show()
                val fragmentManager = requireActivity().supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0 ) {
                    fragmentManager.popBackStackImmediate()
                }
            } else {
                // Failed to add offer
                Toast.makeText(context, "Failed to add offer", Toast.LENGTH_SHORT).show()
            }
        }
    }
}