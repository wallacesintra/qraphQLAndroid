package com.example.graphqlandroid.domain.dto.school

import com.example.graphqlandroid.domain.models.school.AppCounty

data class CreateSchool(
    val county: AppCounty? = null,
    val countyId: String,
    val createdAt: String,
    val id: String,
    val name: String,
    val organizationId: String
)