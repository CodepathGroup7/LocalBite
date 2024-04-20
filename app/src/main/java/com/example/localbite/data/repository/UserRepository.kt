package com.example.localbite.data.repository

import com.example.localbite.data.dao.UserDao
import com.example.localbite.data.model.User
import com.example.localbite.data.model.UserType
import com.example.localbite.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAllUsers()
        }
    }
    suspend fun insertUser(user: User): Long {
        return withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun login(email: String, password: String): LoginViewModel.LoginResult {
        return withContext(Dispatchers.IO) {
            if (userDao.login(email, password) != null) {
                LoginViewModel.LoginResult.Success
            } else {
                LoginViewModel.LoginResult.Error
            }
        }
    }
}