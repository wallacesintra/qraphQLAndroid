package com.example.graphqlandroid.domain.dto.camp

import com.example.graphqlandroid.domain.models.AppCurriculum

data class CreateCampRequestDTO(
    val name: String,
    val startDate: String,
    val endDate: String,
    val curriculum: AppCurriculum,
    val organizationId: String,
    val schoolId: String
)
