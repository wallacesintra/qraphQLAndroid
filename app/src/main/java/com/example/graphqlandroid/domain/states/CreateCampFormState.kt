package com.example.graphqlandroid.domain.states

import com.example.graphqlandroid.domain.dto.camp.CreateCampRequestDTO
import com.example.graphqlandroid.domain.models.AppCurriculum
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.utils.Utils.convertInstantToString
import kotlinx.datetime.Instant

data class CreateCampFormState(
    val name: String = "",
    val startDate: Instant? = null,
    val endDate: Instant? = null,
    val curriculum: AppCurriculum? = null,
    val organization: AppOrganization? = null,
    val school: AppSchool? = null,

    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,

    val showOrganizationList: Boolean = false,
    val showCurriculumList: Boolean = false,
    val showSchoolList: Boolean = false,

    val nameError: String? = null,
    val startDateError: String? = null,
    val endDateError: String? = null,
    val curriculumError: String? = null,
    val organizationError: String? = null,
    val schoolError: String? = null
){
    companion object {
        fun CreateCampFormState.toCreateCampRequestDTO() = CreateCampRequestDTO(
            name = name,
            startDate = convertInstantToString(startDate!!),
            endDate = convertInstantToString(endDate!!),
            curriculum = curriculum!!,
            organizationId = organization!!.id,
            schoolId = school!!.id
        )

    }
}
