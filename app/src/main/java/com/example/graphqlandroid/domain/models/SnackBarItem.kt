package com.example.graphqlandroid.domain.models

data class SnackBarItem(
    val message: String,
    val isError: Boolean = false,
    val isLongVisibility: Boolean = false,
)
