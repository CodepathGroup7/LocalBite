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
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.MainActivity
import com.example.localbite.R
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
    private var userId: String = ""
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

        userRepository = UserRepository()
        restaurantRepository = RestaurantRepository()
        viewModel = ViewModelProvider(this, SignupViewModelFactory(userRepository, restaurantRepository)).get(SignupViewModel::class.java)
        navToRestaurantSignupBtn.setOnClickListener {
            userId = createUser()
            val fragment = SignupRestaurantActivity.newInstance(userId)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.signUpContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        signupBtn.setOnClickListener {
            userId = createUser()
            Log.i("User Created: ", userId)
        }

        return view
    }

    fun createUser(): String {
        var user_id: String = ""
        val username = view?.findViewById<EditText>(R.id.signUpName)
        val phone = view?.findViewById<EditText>(R.id.signUpPhoneNum)
        val email = view?.findViewById<EditText>(R.id.signUpEmail)
        val password = view?.findViewById<EditText>(R.id.signupPassword)
        val confirmPassword = view?.findViewById<EditText>(R.id.signupConfirmPassword)

        if (username != null && phone != null && email != null && password != null && confirmPassword != null && password.text.toString() == confirmPassword.text.toString()) {
            val user = User(name = username.text.toString(), phone = phone.text.toString(), email = email.text.toString(), password = password.text.toString(), userType = userType)
            viewModel.signupUser(user, password.text.toString()) { success, userId ->
                if (success) {
                    user_id = userId
                    if (userType == UserType.CUSTOMER) {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(activity, "Signup User Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(activity, "Input is invalid", Toast.LENGTH_SHORT).show()
        }
        Log.i("User Created: ", user_id)
        return user_id
    }

}