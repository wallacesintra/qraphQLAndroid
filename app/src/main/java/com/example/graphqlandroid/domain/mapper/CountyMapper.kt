package com.example.graphqlandroid.domain.mapper

import com.example.GetCountiesQuery
import com.example.graphqlandroid.domain.models.school.AppCountry
import com.example.graphqlandroid.domain.models.school.AppCounty
import database.CountyEntity

fun CountyEntity.toAppCounty(): AppCounty {
    return AppCounty(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = AppCountry(
            id = countryId,
            name = country ?: ""
        )
    )
}

fun GetCountiesQuery.County.toAppCounty(): AppCounty {
    return AppCounty(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = AppCountry(
            id = countryId,
            name = country?.name ?: ""
        )
    )
}