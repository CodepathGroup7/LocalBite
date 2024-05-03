package com.example.localbite.data.model

import com.google.firebase.database.Exclude

data class Event (
    var id: String = "",
    var eventName: String = "",
    var restaurantName: String = "",
    var eventDate: String = "",
    var eventTime: String = "",
    var eventSummary: String = "",
    var participantList: List<String> = emptyList()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "eventName" to eventName,
            "restaurantName" to restaurantName,
            "eventDate" to eventDate,
            "eventSummary" to eventSummary,
            "participantList" to participantList
        )
    }
}