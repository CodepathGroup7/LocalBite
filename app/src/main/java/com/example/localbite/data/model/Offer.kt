package com.example.localbite.data.model

data class Offer (
    var id: String,
    val restaurantId: String, // ID of the restaurant offering the discount
    val title: String, // Title or name of the offer (e.g., "Special Lunch Deal")
    val description: String, // Description of the offer details
    val imageUrl: String? = null, // URL of the image associated with the offer (optional)
    val startDate: Long, // Timestamp for the start date/time of the offer (in milliseconds)
    val endDate: Long, // Timestamp for the end date/time of the offer (in milliseconds)
    val discountAmount: Double? = null, // Discount amount (e.g., 20% off)
    val couponCode: String? = null,
)