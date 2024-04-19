package com.example.localbite.data.repository

import com.example.localbite.data.dao.RestaurantDao
import com.example.localbite.data.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestaurantRepository(private var restaurantDao: RestaurantDao) {
    suspend fun getAllRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            restaurantDao.getAllRestaurants()
        }
    }

    suspend fun getRestaurantById(id: Int): Restaurant? {
        return withContext(Dispatchers.IO) {
            restaurantDao.getRestaurantById(id)
        }
    }
    suspend fun insertRestaurant(restaurant: Restaurant) {
        withContext(Dispatchers.IO) {
            restaurantDao.insertRestaurant(restaurant)
        }
    }

}