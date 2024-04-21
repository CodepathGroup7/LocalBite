package com.example.localbite

import android.app.Application
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
    }

}