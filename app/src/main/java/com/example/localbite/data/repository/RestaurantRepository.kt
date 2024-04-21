package com.example.localbite.data.repository


import com.example.localbite.data.model.Restaurant
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RestaurantRepository() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://localbite-d92b7-default-rtdb.firebaseio.com").getReference("restaurants")
    fun addRestaurant(restaurant: Restaurant, onComplete: (Boolean, String) -> Unit) {
        val restaurantId = database.child("restaurants").push().key ?: ""
        restaurant.id = restaurantId
        database.child("restaurants").child(restaurantId).setValue(restaurant)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, restaurantId)
                } else {
                    onComplete(false, "Failed to add restaurant")
                }
            }
    }

}