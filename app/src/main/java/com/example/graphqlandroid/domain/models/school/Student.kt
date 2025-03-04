package com.example.graphqlandroid.domain.models.school


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("middleName")
    val middleName: String
)