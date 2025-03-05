package com.example.graphqlandroid.domain.mapper.school

import com.example.CreateSchoolMutation
import com.example.graphqlandroid.domain.dto.school.CreateSchool
import com.example.graphqlandroid.domain.models.school.AppCountry
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.AppSchool

fun CreateSchoolMutation.CreateSchool.toCreateSchool() : CreateSchool {
    return CreateSchool(
        id = id,
        name = name,
        county = county?.let { county ->
            AppCounty(
                id = county.id,
                name = county.name,
                latitude = county.latitude,
                longitude = county.longitude,
                country = AppCountry(
                    id = county.countryId,
                    name = county.country?.name ?: ""
                )
            )
        },
        countyId = countyId,
        createdAt = createdAt,
        organizationId = organizationId,
    )
}


fun CreateSchool.toAppSchool() : AppSchool {
    return AppSchool(
        id = id,
        name = name,
        countyName = county?.name ?: "county name",
        countryName = county?.country?.name ?: "country name",
    )
}