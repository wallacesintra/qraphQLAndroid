package com.example.graphqlandroid.domain.mapper

import com.example.CreateCampMutation
import com.example.graphqlandroid.domain.dto.camp.CreateCamp
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

fun AppCamp.toCampEntity(): CampEntity {
    return CampEntity(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        curriculum = curriculum,
        schoolId = schoolId
    )
}

fun CreateCampMutation.CreateCamp.toCreateCamp(): CreateCamp {
    return CreateCamp(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        curriculum = curriculum.name,
        schoolId = schoolId,
        createdAt = createdAt,
        organizationId = organizationId
    )
}

fun CreateCamp.toAppCamp(): AppCamp {
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
