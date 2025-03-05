package com.example.graphqlandroid.domain.states

import com.example.graphqlandroid.domain.dto.school.CreateSchoolDTO
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.school.AppCounty

data class CreateSchoolFormState(
    val name: String = "",
    val county: AppCounty? = null,
    val organization: AppOrganization? = null,

    val isSelectingCounty: Boolean = false,
    val isSelectingOrganization: Boolean = false,
    val showBottomSheet: Boolean = false,

    val nameError: String? = null,
    val countyError: String? = null,
    val organizationError: String? = null,
){
    companion object {

        fun CreateSchoolFormState.toCreateSchoolDTO(): CreateSchoolDTO {
            return CreateSchoolDTO(
                countyId = county?.id ?: "",
                name = name,
                organizationId = organization?.id ?: ""
            )
        }
    }
}
