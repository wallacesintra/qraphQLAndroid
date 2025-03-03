package com.example.graphqlandroid.domain.dto.authentication

import com.example.graphqlandroid.domain.models.AppUser

data class LoginResponseDTO(
    val token: String,
    val appUser: AppUser
)
