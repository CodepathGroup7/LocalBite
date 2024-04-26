package com.example.localbite

import android.app.Application
import android.util.Log
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp


class LocalBite: Application() {
    companion object{
        lateinit var instance: LocalBite
            private set

    }
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
        Log.i("Google Maps API KEY", getString(R.string.google_maps_api_key))
        Places.initialize(applicationContext, getString(R.string.google_maps_api_key))

    }

}