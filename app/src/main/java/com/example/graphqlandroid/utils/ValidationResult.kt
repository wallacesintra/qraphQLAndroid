package com.example.graphqlandroid.utils

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null,
)
