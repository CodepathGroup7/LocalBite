package com.example.localbite.data.model

data class Event (
    var id: String = "",
    var eventName: String = "",
    var restaurantName: String = "",
    var eventDate: String = "",
    var eventTime: String = "",
    var eventSummary: String = "",
    var participantList: List<User> = emptyList()
)