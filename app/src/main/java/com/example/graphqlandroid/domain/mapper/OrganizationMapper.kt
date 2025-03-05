package com.example.graphqlandroid.domain.mapper

import com.example.FetchOrganizationsQuery
import com.example.graphqlandroid.domain.models.AppOrganization
import database.OrganizationEntity

fun FetchOrganizationsQuery.Organization.toAppOrganization() : AppOrganization {
    return AppOrganization(
        accountType = accountType.name,
        countryId = countryId,
        createdAt = createdAt,
        id = id,
        name = name
    )
}

fun OrganizationEntity.toAppOrganization() : AppOrganization {
    return AppOrganization(
        accountType = accountType,
        countryId = countryId,
        createdAt = createdAt,
        id = id,
        name = name
    )
}