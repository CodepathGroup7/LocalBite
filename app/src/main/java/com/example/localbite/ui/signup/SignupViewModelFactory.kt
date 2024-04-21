package com.example.localbite.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository

class SignupViewModelFactory(private val userRepository: UserRepository, private val restaurantRepository: RestaurantRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(userRepository, restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}