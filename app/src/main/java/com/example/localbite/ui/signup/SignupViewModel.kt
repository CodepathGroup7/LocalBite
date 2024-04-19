package com.example.localbite.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localbite.data.model.Restaurant
import com.example.localbite.data.model.User
import com.example.localbite.data.model.UserType
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(private var userRepository: UserRepository, private var restaurantRepository: RestaurantRepository) : ViewModel() {

    fun signupUser(user: User): Long {
        var userId: Long = 0
        viewModelScope.launch {
            userId = userRepository.insertUser(user)
        }
        return userId
    }

    fun signupRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            restaurantRepository.insertRestaurant(restaurant)
        }
    }
}