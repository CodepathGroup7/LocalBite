package com.example.localbite.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.localbite.LocalBite
import com.example.localbite.MainActivity
import com.example.localbite.R
import com.example.localbite.data.dao.UserDao
import com.example.localbite.data.database.UserDatabase
import com.example.localbite.data.repository.UserRepository
import com.example.localbite.ui.signup.SignupActivity

class LoginActivity: ComponentActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val emailInput = findViewById<EditText>(R.id.loginEmail)
        val passwordInput = findViewById<EditText>(R.id.loginPassword)
        val loginButton = findViewById<Button>(R.id.loginBtn)
        val signupText = findViewById<TextView>(R.id.signupText)
        val database = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "users").build()
        userDao = database.userDao()
        val repository = UserRepository(userDao)
        val viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                LoginViewModel.LoginResult.Success -> {
                    // Login successful, navigate to logged-in screens
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                LoginViewModel.LoginResult.Error -> {
                    // Login failed, display error message
                    Log.e("Login Error", "Login failed!")
                }
            }
        })

        signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.login(email, password)
        }
    }
}