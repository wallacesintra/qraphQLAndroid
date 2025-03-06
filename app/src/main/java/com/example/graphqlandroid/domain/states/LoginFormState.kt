package com.example.graphqlandroid.domain.states

import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,

    /* Errors*/

    val emailError: String? = null,
    val passwordError: String? = null
){
    companion object {
        fun LoginFormState.toLogInInputRequestDTO(): LoginInputRequestDTO {
            return LoginInputRequestDTO(
                email = email,
                password = password
            )
        }
    }
}
