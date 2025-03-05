package com.example.graphqlandroid.domain.models.camps

data class Camp(
    val campGroups: List<CampGroup>,
    val createdAt: String,
    val curriculum: String,
    val endDate: String,
    val id: String,
    val name: String,
    val organization: Organization,
    val organizationId: String,
    val school: School,
    val schoolId: String,
    val startDate: String,
    val students: List<Student>
)