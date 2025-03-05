package com.example.graphqlandroid.domain.actions

import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.school.AppCounty

sealed interface CreateSchoolAction {
    data class OnSchoolNameChange(val schoolName: String): CreateSchoolAction

    data class OnCountyChange(val county: AppCounty): CreateSchoolAction

    data class OnOrganizationChange(val organization: AppOrganization): CreateSchoolAction

    data class OnShowOrganizationList(val show: Boolean): CreateSchoolAction

    data class OnShowCountyList(val show: Boolean): CreateSchoolAction

    data class OnShowBottomSheet(val show: Boolean): CreateSchoolAction

    data object Submit: CreateSchoolAction
}