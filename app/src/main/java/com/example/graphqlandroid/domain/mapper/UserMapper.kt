package com.example.graphqlandroid.domain.mapper

import com.example.LoginMutation
import com.example.graphqlandroid.domain.models.User

fun LoginMutation.User.toUser(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone
    )
}