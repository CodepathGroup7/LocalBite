package com.example.localbite.data.repository


import android.content.ContentValues.TAG
import android.util.Log
import com.example.localbite.data.model.Restaurant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RestaurantRepository() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://localbite-d92b7-default-rtdb.firebaseio.com").reference
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

    fun getAllRestaurants(callback: (List<Restaurant>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val restaurantsRef = database.getReference("restaurants")

        restaurantsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val restaurants = mutableListOf<Restaurant>()
                for (restaurantSnapshot in snapshot.children) {
                    val restaurant = restaurantSnapshot.getValue(Restaurant::class.java)
                    restaurant?.let { restaurants.add(it) }
                }
                callback(restaurants)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error getting all restaurants: $error")
                callback(emptyList())
            }
        })
    }

    // Function to get restaurant by userId
    fun getRestaurantByUserId(userId: String, callback: (Restaurant?) -> Unit) {
        database.child("restaurants").orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (restaurantSnapshot in snapshot.children) {
                        val restaurant = restaurantSnapshot.getValue(Restaurant::class.java)
                        callback(restaurant)
                        return // Return the first restaurant found with the given userId
                    }
                }
                callback(null) // No restaurant found with the given userId
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null) // Error occurred while retrieving restaurant
            }
        })
    }

    // Function to get restaurant by id
    fun getRestaurantById(restaurantId: String, callback: (Restaurant?) -> Unit) {
        database.child("restaurants").orderByChild("id").equalTo(restaurantId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (restaurantSnapshot in snapshot.children) {
                        val restaurant = restaurantSnapshot.getValue(Restaurant::class.java)
                        callback(restaurant)
                        return // Return the first restaurant found with the given userId
                    }
                }
                callback(null)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(null) // Error occurred while retrieving restaurant
            }
        })
    }

}