package com.example.graphqlandroid.domain.models.school


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppCamp(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("curriculum")
    val curriculum: String,
    @SerialName("students")
    val students: List<Student> = emptyList(),

    val schoolId: String,
)