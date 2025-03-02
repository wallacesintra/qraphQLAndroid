package com.example.graphqlandroid.domain.dto.authentication

import com.example.graphqlandroid.domain.models.User

data class LoginResponseDTO(
    val token: String,
    val user: User
)
