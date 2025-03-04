package com.example.graphqlandroid.domain.models.school


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppCountry(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
)