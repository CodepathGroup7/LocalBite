package com.example.localbite.data.model

data class User(
    var id: String = "", // Firebase-generated ID
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var password: String = "",
    var userType: UserType = UserType.CUSTOMER // Default to Customer
)

enum class UserType {
    CUSTOMER,
    RESTAURANT
}