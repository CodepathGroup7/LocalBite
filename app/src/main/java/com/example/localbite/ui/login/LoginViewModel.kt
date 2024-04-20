package com.example.localbite.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localbite.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private var loginRepository: UserRepository): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(username, password)
            _loginResult.value = result
        }
    }

    sealed class LoginResult {
        object Success : LoginResult()
        object Error : LoginResult()
        // You can add more specific error types here (e.g., InvalidCredentials, NetworkError)
    }
}