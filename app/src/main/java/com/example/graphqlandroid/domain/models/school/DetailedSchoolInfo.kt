package com.example.graphqlandroid.domain.models.school


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedSchoolInfo(
    @SerialName("school")
    val school: DetailedSchool
)