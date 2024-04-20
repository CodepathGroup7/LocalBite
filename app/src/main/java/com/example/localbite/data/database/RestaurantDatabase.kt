package com.example.localbite.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localbite.data.dao.RestaurantDao
import com.example.localbite.data.model.Restaurant
import com.example.localbite.data.model.User

@Database(entities = [Restaurant::class, User::class], version = 1)
abstract class RestaurantDatabase: RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}