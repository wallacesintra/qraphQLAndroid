package com.example.graphqlandroid.domain.actions

import com.example.LoginMutation

sealed interface LoginAction {
    data class OnEmailChange(val email: String): LoginAction
    data class OnPasswordChange(val password: String): LoginAction
    data class OnPasswordVisibilityChange(val show: Boolean): LoginAction

    data object OnSubmit: LoginAction
}