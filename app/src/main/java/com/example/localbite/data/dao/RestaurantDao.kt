package com.example.localbite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.localbite.data.model.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurants")
    suspend fun getAllRestaurants(): List<Restaurant>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getRestaurantById(id: Int): Restaurant
    @Insert
    suspend fun insertRestaurant(restaurant: Restaurant)


}