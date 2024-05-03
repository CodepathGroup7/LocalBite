package com.example.localbite.data.repository

import android.util.Log
import com.example.localbite.data.model.Event
import com.example.localbite.data.model.Offer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OfferRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://localbite-d92b7-default-rtdb.firebaseio.com").reference

    fun addOffer(offer: Offer, onComplete: (Boolean, String) -> Unit) {
        val offerId = database.child("offers").push().key ?: ""
        offer.id = offerId
        database.child("offers").child(offerId).setValue(offer)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, offerId)
                } else {
                    onComplete(false, "Failed to add event")
                }
            }
    }

    // Function to retrieve a discount offer by its ID
    fun getOfferById(offerId: String, callback: (Offer?) -> Unit) {
        database.child(offerId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val offer = snapshot.getValue(Offer::class.java)
                callback(offer)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null) // Error occurred while retrieving offer
            }
        })
    }

    fun getAllOffersByRestaurantId(restaurantId: String, callback: (List<Offer>) -> Unit) {
        database.child("offers").orderByChild("restaurantId").equalTo(restaurantId).addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val offers = mutableListOf<Offer>()
                for (offerSnapshot in snapshot.children) {
                    val offer = offerSnapshot.getValue(Offer::class.java)
                    offer?.let { offers.add(it) }
                }
                callback(offers)
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("OfferRepository", "Failed to retrieve offers", error.toException())
            }
        })
    }

    fun getAllOffers(callback: (List<Offer>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val offersRef = database.getReference("offers")

        offersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val offers = mutableListOf<Offer>()
                for (offerSnapshot in snapshot.children) {
                    val offer = offerSnapshot.getValue(Offer::class.java)
                    offer?.let { offers.add(it) }
                }
                callback(offers)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("OfferRepository", "Failed to retrieve offers", error.toException())
            }
        })
    }
}