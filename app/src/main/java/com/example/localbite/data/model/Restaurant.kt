package com.example.localbite.data.model

data class Restaurant(
    var id: String = "", // Firebase-generated ID
    var userId: String = "", // ID of the restaurant's owner
    var name: String = "",
    var address: Map<String, String> = emptyMap(), // Address as a map of key-value pairs
    var description: String = "",
    var imageUrl: String = ""
)
