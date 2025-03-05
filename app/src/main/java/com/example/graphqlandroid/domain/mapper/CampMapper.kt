package com.example.graphqlandroid.domain.mapper

import com.example.CreateCampMutation
import com.example.FetchCampQuery
import com.example.FetchDetailedCampInfoQuery
import com.example.graphqlandroid.domain.dto.camp.CreateCamp
import com.example.graphqlandroid.domain.models.camps.DetailedCampInfo
import com.example.graphqlandroid.domain.models.camps.OrganizationLittleInfo
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.school.AppSchool
import database.CampEntity
import database.DetailedCampEntity

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

fun FetchDetailedCampInfoQuery.Data.toDetailedCampInfo(): DetailedCampInfo {
    return DetailedCampInfo(
        campGroupsSize = camp?.campGroups?.size ?: 0,
        createdAt = camp?.createdAt ?: "",
        curriculum = camp?.curriculum?.toAppCurriculum()?.text ?: "",
        endDate = camp?.endDate ?: "",
        id = camp?.id ?: "",
        name = camp?.name ?: "",
        organization = camp?.organization?.let {
            OrganizationLittleInfo(
                id = camp.organizationId,
                name = camp.organization.name
            )
        },
        organizationId = camp?.organizationId ?: "",
        school = camp?.school?.let {
            AppSchool(
                id = camp.schoolId,
                name = camp.school.name,
                countyName = "",
                countryName = ""
            )
        },
        schoolId = camp?.schoolId ?: "",
        startDate = camp?.startDate ?: "",
        studentsSize = camp?.students?.size ?: 0
    )
}


fun DetailedCampEntity.toDetailedCampInfo(): DetailedCampInfo {
    return DetailedCampInfo(
        campGroupsSize = campGroupsSize.toInt(),
        createdAt = createdAt,
        curriculum = curriculum,
        endDate = endDate,
        id = id,
        name = name,
        organization = OrganizationLittleInfo(
            id = organizationId,
            name = organizationName
        ),
        organizationId = organizationId,
        school = AppSchool(
            id = schoolId,
            name = schoolName,
            countyName = "",
            countryName = ""
        ),
        schoolId = schoolId,
        startDate = startDate,
        studentsSize = studentsSize.toInt()
    )
}


fun FetchCampQuery.Camp.toAppCamp(): AppCamp {
    return AppCamp(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        curriculum = curriculum.toAppCurriculum().text,
        students = emptyList(),
        schoolId = schoolId
    )
}
