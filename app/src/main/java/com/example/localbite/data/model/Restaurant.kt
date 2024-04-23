package com.example.localbite.data.model

data class Restaurant(
    var id: String = "", // Firebase-generated ID
    var userId: String = "", // ID of the restaurant's owner
    var name: String = "",
    var address: String = "",
    var description: String = "",
    var imageUrl: String = ""
)
