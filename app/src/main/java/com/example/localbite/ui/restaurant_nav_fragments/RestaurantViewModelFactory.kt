package com.example.localbite.ui.restaurant_nav_fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.OfferRepository
import com.example.localbite.data.repository.RestaurantRepository

class RestaurantViewModelFactory(private val eventRepository: EventRepository, private val offerRepository: OfferRepository, private val restaurantRepository: RestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantViewModel(eventRepository, offerRepository, restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
