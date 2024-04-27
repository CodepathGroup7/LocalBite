package com.example.localbite.data.model

import android.os.Parcelable

data class Event (
    var id: String = "",
    var eventName: String = "",
    var restaurantName: String = "",
    var eventDate: String = "",
    var eventTime: String = "",
    var eventSummary: String = "",
    var participantList: MutableList<String> = mutableListOf()
)