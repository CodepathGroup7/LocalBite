package com.example.localbite.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.localbite.MainActivity
import com.example.localbite.R
import com.example.localbite.data.repository.UserRepository
import com.example.localbite.ui.signup.SignupActivity

class LoginActivity: ComponentActivity() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val emailInput = findViewById<EditText>(R.id.loginEmail)
        val passwordInput = findViewById<EditText>(R.id.loginPassword)
        val loginButton = findViewById<Button>(R.id.loginBtn)
        val signupText = findViewById<TextView>(R.id.signupText)

        val repository = UserRepository() // Initialize FirebaseRepository
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repository)).get(LoginViewModel::class.java)

        // Observe loginResult LiveData
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginResult.Success -> {
                    val userId = result.userId
                    // Handle successful login (e.g., navigate to next screen)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                is LoginResult.Error -> {
                    val errorMessage = result.errorMessage
                    // Display error message to the user (e.g., show toast)
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.loginUser(email, password)
        }
    }
}