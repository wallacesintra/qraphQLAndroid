package com.example.graphqlandroid.domain.mapper.school

import com.example.GetDetailedSchoolInfoQuery
import com.example.GetSchoolsQuery
import com.example.graphqlandroid.domain.mapper.toAppCurriculum
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.school.AppCountry
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import database.SchoolEntity

fun GetSchoolsQuery.School. toSchool(): AppSchool{
    return AppSchool(
        id = id,
        name = name,
        countyName = county?.name ?: "",
        countryName = county?.country?.name ?: ""
    )
}

fun SchoolEntity.toSchool(): AppSchool {
    return AppSchool(
        id = id,
        name = name,
        countyName = countyName,
        countryName = countryName
    )
}


fun GetDetailedSchoolInfoQuery.School.toDetailedSchool(): DetailedSchool {
    return DetailedSchool(
        id = id,
        countyId = countyId,
        name = name,
        organizationId = organizationId,
        county = county?.let {
            AppCounty(
                id = county.id,
                name = county.name,
                latitude = county.latitude,
                longitude = county.longitude,
                country = county.country?.let {
                    AppCountry(
                        id = county.country.id,
                        name = county.country.name
                    )
                }
            )
        },
        camps = camps?.let { camps ->
            camps.mapNotNull { camp ->
                camp?.let {
                    AppCamp(
                        id = camp.id,
                        name = camp.name,
                        startDate = camp.startDate,
                        endDate = camp.endDate,
                        curriculum = camp.curriculum.toAppCurriculum().text,
                        students = emptyList(),
                        schoolId = camp.schoolId
                    )

                }
            }
        } ?: emptyList()
    )
}