package com.example.localbite.data.model

data class Restaurant(
    var id: String = "", // Firebase-generated ID
    var userId: String = "", // ID of the restaurant's owner
    var name: String = "",
    var description: String = "",
    var imageUrl: String = "",
    val address: Address = Address()
) {
    data class Address(
        val address: String = "",
        val lat: String = "",
        val lng: String = "",
        val street_name: String = ""
    )
}

