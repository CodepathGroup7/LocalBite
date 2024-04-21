package com.example.localbite.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localbite.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private var repository: UserRepository): ViewModel() {


    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    fun loginUser(email: String, password: String) {
        repository.loginUser(email, password) { success, userId ->
            if (success && !userId.isNullOrEmpty()) {
                _loginResult.value = LoginResult.Success(userId)
            } else {
                _loginResult.value = LoginResult.Error("Login failed. Please check your credentials.")
            }
        }
    }
}

sealed class LoginResult {
    data class Success(val userId: String) : LoginResult()
    data class Error(val errorMessage: String) : LoginResult()
}