package com.example.graphqlandroid.domain.models

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: String,
    val trained: Boolean,
    val organizationId: String
)
