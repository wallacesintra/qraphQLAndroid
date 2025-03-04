package com.example.graphqlandroid.domain.mapper

import com.example.graphqlandroid.domain.models.school.AppCamp
import database.CampEntity

fun CampEntity.toAppCamp(): AppCamp {
    return AppCamp(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        curriculum = curriculum,
        students = emptyList(),
        schoolId = schoolId
    )
}