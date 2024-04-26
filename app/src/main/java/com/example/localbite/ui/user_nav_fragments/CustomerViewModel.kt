package com.example.localbite.ui.user_nav_fragments

import androidx.lifecycle.ViewModel
import com.example.localbite.data.model.Restaurant
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.RestaurantRepository

class CustomerViewModel(private val restaurantRepository: RestaurantRepository, private val eventRepository: EventRepository): ViewModel() {

    fun getAllRestaurants(callback: (List<Restaurant>) -> Unit) {
        restaurantRepository.getAllRestaurants { restaurants ->
            callback(restaurants)
        }
    }

    fun getRestaurantById(id: String, callback: (Restaurant?) -> Unit) {
        restaurantRepository.getRestaurantById(id) { restaurant ->
            callback(restaurant)
        }
    }
}