package com.example.localbite.ui.restaurant_nav_fragments

import androidx.lifecycle.ViewModel
import com.example.localbite.data.model.Event
import com.example.localbite.data.model.Offer
import com.example.localbite.data.model.Restaurant
import com.example.localbite.data.repository.EventRepository
import com.example.localbite.data.repository.OfferRepository
import com.example.localbite.data.repository.RestaurantRepository

class RestaurantViewModel(private var eventsRepository: EventRepository, private var offerRepository: OfferRepository, private var restaurantRepository: RestaurantRepository): ViewModel() {

    // Function to add a new offer
    fun addOffer(offer: Offer, onComplete: (Boolean, String) -> Unit) {
        offerRepository.addOffer(offer) { success, offerId ->
            onComplete(success, offerId)
        }
    }

    // Function to retrieve an offer by its ID
    fun getOfferById(offerId: String, callback: (Offer?) -> Unit) {
        offerRepository.getOfferById(offerId) { offer ->
            callback(offer)
        }
    }

    // Function to retrieve all offers
    fun getAllOffers(callback: (List<Offer>) -> Unit) {
        offerRepository.getAllOffers { offers ->
            callback(offers)
        }
    }

    // Function to add a new event
    fun addEvent(event: Event, onComplete: (Boolean, String) -> Unit) {
        eventsRepository.addEvent(event) { success, eventId ->
            onComplete(success, eventId)
        }
    }

    // Function to retrieve an event by its ID
    fun getEventById(eventId: String, callback: (Event?) -> Unit) {
        eventsRepository.getEventById(eventId) { event ->
            callback(event)
        }
    }

    // Function to retrieve all events
    fun getAllEvents(callback: (List<Event>) -> Unit) {
        eventsRepository.getAllEvents { events ->
            callback(events)
        }
    }

    // Function to get restaurant using userId
    fun getRestaurantByUserId(userId: String, callback: (Restaurant?) -> Unit) {
        restaurantRepository.getRestaurantByUserId(userId) { restaurant ->
            callback(restaurant)
        }
    }
}