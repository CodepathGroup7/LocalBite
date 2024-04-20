package com.example.localbite

import android.app.Application
import androidx.room.Room
import com.example.localbite.data.database.RestaurantDatabase
import com.example.localbite.data.database.UserDatabase

class LocalBite: Application() {
    companion object{
        @Volatile
        @JvmStatic
        lateinit var instance: LocalBite
        lateinit var userDatabase: UserDatabase
        lateinit var restaurantDatabase: RestaurantDatabase
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        initRoom()
    }

    fun initRoom(){
        userDatabase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "users"
        ).build()

        restaurantDatabase = Room.databaseBuilder(
            applicationContext,
            RestaurantDatabase::class.java,
            "restaurants"
        ).build()
    }
}