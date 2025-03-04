package com.example.graphqlandroid.domain.models.school


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedSchool(
    @SerialName("id")
    val id: String,
    @SerialName("countyId")
    val countyId: String,
    @SerialName("name")
    val name: String,
    @SerialName("organizationId")
    val organizationId: String,
    @SerialName("county")
    val county: AppCounty? = null,
    @SerialName("camps")
    val camps: List<AppCamp> = emptyList()
)