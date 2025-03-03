package com.example.graphqlandroid.domain.mapper

import com.example.LoginMutation
import com.example.graphqlandroid.domain.models.User
import database.LogInUserEntity

fun LoginMutation.User.toUser(): User {
    return User(
        id = id ,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        gender = gender.name,
        trained = trained,
        organizationId = organizationId
    )
}

fun LogInUserEntity.toUser(): User{
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        gender = gender,
        trained = trained,
        organizationId = organizationId
    )
}