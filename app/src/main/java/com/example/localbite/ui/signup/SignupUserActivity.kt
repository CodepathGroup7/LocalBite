package com.example.localbite.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.localbite.LocalBite
import com.example.localbite.MainActivity
import com.example.localbite.R
import com.example.localbite.data.dao.RestaurantDao
import com.example.localbite.data.dao.UserDao
import com.example.localbite.data.database.RestaurantDatabase
import com.example.localbite.data.database.UserDatabase
import com.example.localbite.data.model.User
import com.example.localbite.data.model.UserType
import com.example.localbite.data.repository.RestaurantRepository
import com.example.localbite.data.repository.UserRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SignupUserActivity: Fragment() {
    private lateinit var userType: UserType
    private lateinit var viewModel: SignupViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var restaurantRepository: RestaurantRepository
    private var userId: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup_user, container, false)
        val signupBtn = view.findViewById<Button>(R.id.signUpBtn)
        val navToRestaurantSignupBtn = view.findViewById<FloatingActionButton>(R.id.nav_to_restaurant_signup)
        val userTypeRG = view.findViewById<RadioGroup>(R.id.user_type_rg)
        userTypeRG.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.userTypeRestaurant) {
                navToRestaurantSignupBtn.visibility = View.VISIBLE
                signupBtn.visibility = View.GONE
                userType = UserType.RESTAURANT
            } else {
                navToRestaurantSignupBtn.visibility = View.GONE
                signupBtn.visibility = View.VISIBLE
                userType = UserType.CUSTOMER
            }
        }
        val appContext = LocalBite.instance.applicationContext
        val userDatabase = Room.databaseBuilder(appContext, UserDatabase::class.java, "users").build()
        val restaurantDatabase = Room.databaseBuilder(appContext, RestaurantDatabase::class.java, "restaurants").build()
        userRepository = UserRepository(userDatabase.userDao())
        restaurantRepository = RestaurantRepository(restaurantDatabase.restaurantDao())
        viewModel = SignupViewModel(userRepository, restaurantRepository)
        navToRestaurantSignupBtn.setOnClickListener {
            userId = createUser()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.signUpContainer, SignupRestaurantActivity(userId))
            transaction.addToBackStack(null)
            transaction.commit()
        }

        signupBtn.setOnClickListener {
            userId = createUser()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    fun createUser(): Long {
        var userId: Long = 0
        val username = view?.findViewById<EditText>(R.id.signUpName)
        val phone = view?.findViewById<EditText>(R.id.signUpPhoneNum)
        val email = view?.findViewById<EditText>(R.id.signUpEmail)
        val password = view?.findViewById<EditText>(R.id.signupPassword)
        val confirmPassword = view?.findViewById<EditText>(R.id.signupConfirmPassword)

        if (username != null && phone != null && email != null && password != null && confirmPassword != null && password.text.toString() == confirmPassword.text.toString()) {
            val user = User(name = username.text.toString(), phone = phone.text.toString(), email = email.text.toString(), password = password.text.toString(), userType = userType)
            userId = viewModel.signupUser(user)
        } else {
            Toast.makeText(activity, "Input is invalid", Toast.LENGTH_SHORT).show()
        }
        Log.i("User Created: ", userId.toString())
        return userId
    }
}