package com.example.graphqlandroid.domain.models.camps

import com.example.graphqlandroid.domain.models.school.AppSchool

data class DetailedCampInfo(
    val campGroupsSize: Int,
    val createdAt: String,
    val curriculum: String,
    val endDate: String,
    val id: String,
    val name: String,
    val organization: OrganizationLittleInfo? = null,
    val organizationId: String,
    val school: AppSchool? = null,
    val schoolId: String,
    val startDate: String,
    val studentsSize: Int
)