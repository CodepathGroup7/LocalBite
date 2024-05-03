# Milestone 1 - LocalBite 

## Table of Contents

1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview

### Description
In the competitive restaurant landscape, family-owned establishments often face challenges in attracting and retaining customers, especially during their initial stages of operation. To address this, we propose "LocalBite", a mobile application tailored specifically for newly opened family-owned restaurants.

### App Evaluation
- **Category:** Local Restaurants/Business
- **Mobile:** For the creation of the restaurant profile, pictures of the restaurant as well as menu items can be added to attract customers all from your phone. Those looking for new restaurants to eat at can also use their phone to browse locations on the move
- **Story:** LocalBite serves as a comprehensive platform that empowers local restaurants to enhance visibility, promote special events, and cultivate meaningful connections with their communities. Through intuitive features, including event promotion, exclusive discounts, and interactive engagement tools, our app enables restaurants to showcase their unique offerings and reach a broader audience.
- **Market:** Local restaurants face challenges in attracting customers and growing bigger. Let's take Redmond, WA for example, there are around 72000 residents, and it has a tech savy population based on the companies around Seattle, Bellevue and Redmond. There would be around 200 family owned restaurants and Redmond has a strong sense of community. So, people usually go to these kind of food joints.
- **Habit:** An average american takes out/dine in about 4.2 times per week, so people would use this app atleast 1 time per week and the average users would be mainly consuming through the app and the restaurants owners would be creating through the app.
- **Scope:** The user will be able to view nearby restaurants through the app and view their events or discount offers and register for events. The restaurants will be able to create events and discount coupons and view participation.


## Product Spec

### 1. User Features (Required and Optional)

**Required Features**
1. User profile creation and management - Customer can create a profile and manage the data and properties
2. Restaurant profile creation and management - Restaurants can create a profile and manage the data and properties
3. Event promotion and management - Restaurants can create and promote their events and view the participation
4. Discounts and special offers showcase - Restaurants can create and promote their discount or special offers
5. Interactive map - Customer can view nearby restaurants and navigate to specific restaurant for more details about events and discounts
6. Customer reviews and ratings - Customer can give reviews and ratings to restaurants
7. Push notifications - Customers will get push notifications whenever a new event or discount offer is available
8. Reservation system - Customer can reserve a spot for an event and restaurants can view the list of participants

**Optional Features**
1. Community engagement tools - Announcement or chat thread for community building with conversations about must-try food, and other recommendations
2. Social sharing integration - Link to social media accounts
3. Analytics dashboard for restaurants - Restaurants can view the overall footfall and engagement through the app

### 2. Screen Archetypes

- Login Screen:
    - User can log in to their account
- Register Screen:
    - User can register for an account
- Stream:
    - Customer can view a feed of events and discount or special offers
    - Restaurants can view a feed of participants, and their own created events and offers
- Creation:
    - Users can create or manage their profile
    - Restaurants can create and post events and discount or special offers
    - Customers can reserve a spot for an event
- Map View:
    - Customers can view a map with markers for nearby restaurants
- Calendar Component:
    - Restaurants can create an event for a particular calendar date and time
- Notification:
    - Customers will get notified whenever their is a new event or discount offer
    - Restaurants will get notified whenever their is a new participant for the event

### 3. Navigation

**Tab Navigation** (Tab to Screen)
Customer
- Home: Map view for nearby restaurants
- Events/Discounts: Recycler view for events and discounts
- Profile: Customer can view and edit their profile

Restaurant
- Home: Displays the events and discounts created by the restaurant
- Profile - Restaurants can view and edit their profile
    
**Flow Navigation** (Screen to Screen)
Customer
- Login Screen
    - Register/SignUp
    - Home
- Register/SignUp
    - Home
- Home
    - Restaurants Profile Page
- Restaurants Profile Page (shows restaurant profile, events and discounts)
    - Events Details Page
- Events Page/Discount Page
    - Events Details Page
- Events Detail Page
    - Reserve a spot page
- Profile Page
    - None

Restaurant
- Login Screen
    - Register/SignUp
    - Home
- Register/SignUp
    - Home
- Home (shows both events and discounts using top bar navigation)
    - Events Details Page
    - Create an event or discount based on the current top bar navigation
- Profile
    - None

## Wireframes

### Login/Registration
![Login](https://github.com/CodepathGroup7/LocalBite/blob/main/wireframes/LoginHD.jpg)
<br>

### Customer
![Customer](https://github.com/CodepathGroup7/LocalBite/blob/main/wireframes/CustomerHd.jpg)

<br>

### Restaurant
![Restaurant](https://github.com/CodepathGroup7/LocalBite/blob/main/wireframes/RestaurantHD.jpg)

<br>

<br>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

<br>

# Milestone 2 - Build Sprint 1 (Unit 8)
## GitHub Project board
![ProjectBoard](https://github.com/CodepathGroup7/LocalBite/blob/main/wireframes/ProjectBoard.png)
<br>
## Issue cards
![ProjectBoard](https://github.com/CodepathGroup7/LocalBite/blob/main/wireframes/ProjectBoard.png)
<br>
![Unit9ProjectBoard](https://github.com/CodepathGroup7/LocalBite/blob/main/wireframes/Unit9ProjectBoard.png)
<br>
## Issues worked on this sprint
List the issues you completed this sprint
- [x] Login and Registration for Customers
- [x] Login and Registration for Restaurants

### Demo GIF
![ProjectBoard](https://github.com/CodepathGroup7/LocalBite/blob/main/DemoGif/LocalBite_Login_and_Registration.gif)

# Milestone 3 - Build Sprint 2 (Unit 9)
## GitHub Project board
[Add screenshot of your Project Board with the updated status of issues for Milestone 3. Note that these should include the updated issues you worked on for this sprint and not be a duplicate of Milestone 2 Project board.] 

## Completed user stories
List the stories you completed this sprint
- [x] Restaurants are shown as markers in the Map View
- [x] Creating & Displaying Events on both the Customer and Restaurant side
- [x] Registering for an event through Customer login

List the stories that you will complete in the next sprint
- [x] Creating & Displaying Offers on both the Customer and Restaurant side
- [x] Push Notifications when any event or offer is published
[Add video/gif of your current application that shows build progress]

## App Demo Video
![DemoVideo](https://youtu.be/dab1uPx5Ch8)

Link to the video: [Demo Video](https://www.youtube.com/watch?v=dab1uPx5Ch8&ab_channel=SutikshV)
