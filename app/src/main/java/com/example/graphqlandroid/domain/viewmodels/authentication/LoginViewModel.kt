package com.example.graphqlandroid.domain.viewmodels.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.actions.LoginAction
import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.SnackBarItem
import com.example.graphqlandroid.domain.states.LoginFormState
import com.example.graphqlandroid.domain.states.LoginFormState.Companion.toLogInInputRequestDTO
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import com.example.graphqlandroid.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch

class LoginViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource,
    private val snackBarHandler: SnackBarHandler
): ViewModel() {


    var loginFormSate by mutableStateOf(LoginFormState())
    val loginResponseStateFlow = MutableStateFlow(Results.initial<LoginResponseDTO>())

    fun onLoginActions(loginAction: LoginAction){
        when(loginAction){
            is LoginAction.OnEmailChange -> {
                loginFormSate = loginFormSate.copy(
                    email = loginAction.email
                )
            }

            is LoginAction.OnPasswordChange -> {
                loginFormSate = loginFormSate.copy(
                    password = loginAction.password
                )
            }
            is LoginAction.OnPasswordVisibilityChange -> {
                loginFormSate = loginFormSate.copy(
                    isPasswordVisible = loginAction.show
                )
            }
            LoginAction.OnSubmit -> { login() }
        }
    }

    private fun login() {

        if (loginFormSate.password.isBlank()){
            loginFormSate = loginFormSate.copy(
                passwordError = "Please enter your password"
            )
        }


        if (loginFormSate.email.isBlank()){
            loginFormSate = loginFormSate.copy(
                emailError = "Please enter your email"
            )
        }

        if (!Utils.isValidEmail(email = loginFormSate.email)) {
            loginFormSate = loginFormSate.copy(
                emailError = "Please enter a valid email address"
            )
        }


        if ( !loginFormSate.emailError.isNullOrEmpty() || !loginFormSate.passwordError.isNullOrEmpty()){
            return
        }



        viewModelScope.launch {
            loginResponseStateFlow.value = Results.loading()
            remoteRepository.login(loginInput = loginFormSate.toLogInInputRequestDTO())
                .catch {loginResponseStateFlow.value = Results.error()  }
                .collect{response ->
                    loginResponseStateFlow.value = response

                    snackBarHandler.showSnackBarNotification(
                        SnackBarItem(
                            message = if (response.status == ResultStatus.SUCCESS) "Login Successful" else "Login failed",
                            isError = response.status != ResultStatus.SUCCESS
                        )
                    )

                    response.data?.let { data ->
                        databaseSource.insertUser(user = data.user)
                    }

                }

        }
    }

}