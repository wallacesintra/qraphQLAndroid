package com.example.graphqlandroid.domain.mapper

import com.example.GetUserQuery
import com.example.LoginMutation
import com.example.graphqlandroid.domain.models.AppUser
import com.example.type.User
import database.LogInUserEntity

fun LoginMutation.User.toUser(): AppUser {
    return AppUser(
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

fun LogInUserEntity.toUser(): AppUser {
    return AppUser(
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

fun GetUserQuery.User.toUser(): AppUser{
    return AppUser(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        gender = gender.name,
        trained = trained,
        organizationId = organizationId
    )
}