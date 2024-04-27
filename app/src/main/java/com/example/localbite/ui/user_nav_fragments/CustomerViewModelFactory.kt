package com.example.localbite.ui.user_nav_fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.OfferRepository
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository
import com.example.localbite.ui.restaurant_nav_fragments.RestaurantViewModel



class CustomerViewModelFactory(private val restaurantRepository: RestaurantRepository, private val eventRepository: EventRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerViewModel(restaurantRepository, eventRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


