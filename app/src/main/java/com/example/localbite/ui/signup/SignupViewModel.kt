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

    // Function to sign up a new user
    fun signupUser(user: User, password: String, onComplete: (Boolean, String) -> Unit) {
        userRepository.addUser(user, password) { success, userId ->
            if (success) {
                onComplete(true, userId)
            } else {
                onComplete(false, "User signup failed")
            }
        }
    }

    // Function to sign up a new restaurant
    fun signupRestaurant(restaurant: Restaurant, onComplete: (Boolean, String) -> Unit) {
        restaurantRepository.addRestaurant(restaurant) { success, restaurantId ->
            if (success) {
                onComplete(true, restaurantId)
            } else {
                onComplete(false, "Restaurant signup failed")
            }
        }
    }
}