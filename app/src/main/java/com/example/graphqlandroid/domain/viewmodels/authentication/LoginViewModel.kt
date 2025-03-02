package com.example.graphqlandroid.domain.viewmodels.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    init {
        login()
    }

    fun login() {


        viewModelScope.launch(Dispatchers.IO) {
            val response = remoteRepository.login(loginInput =
                LoginInputRequestDTO (
                    email = "instructor@example.com",
                    password = "hashedpassword123"
                )
            )

            println("login response : $response")
        }
    }

}