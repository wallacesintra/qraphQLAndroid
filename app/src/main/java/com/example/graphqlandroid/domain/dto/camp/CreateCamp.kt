package com.example.graphqlandroid.domain.dto.camp

data class CreateCamp(
    val id: String,
    val name: String,
    val startDate: String,
    val endDate: String,
    val createdAt: String,
    val curriculum: String,
    val organizationId: String,
    val schoolId: String
)