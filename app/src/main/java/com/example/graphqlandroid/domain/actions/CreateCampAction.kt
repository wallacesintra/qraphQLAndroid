package com.example.graphqlandroid.domain.actions

import com.example.graphqlandroid.domain.models.AppCurriculum
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.school.AppSchool
import kotlinx.datetime.Instant

sealed interface CreateCampAction {

    data class OnNameChange(val name: String) : CreateCampAction

    data class OnStartDateChange(val startDate: Instant) : CreateCampAction

    data class IsStartDatePickerVisible(val isVisible: Boolean) : CreateCampAction

    data class OnEndDateChange(val endDate: Instant) : CreateCampAction

    data class IsEndDatePickerVisible(val isVisible: Boolean) : CreateCampAction

    data class OnCurriculumChange(val curriculum: AppCurriculum) : CreateCampAction

    data class OnOrganizationIdChange(val organizationId: AppOrganization) : CreateCampAction

    data class OnSchoolChange(val schoolId: AppSchool) : CreateCampAction

    data class IsSchoolPickerVisible(val isVisible: Boolean) : CreateCampAction

    data class IsCurriculumPickerVisible(val isVisible: Boolean) : CreateCampAction

    data class IsOrganizationPickerVisible(val isVisible: Boolean) : CreateCampAction

    data class OnShowBottomSheetChange(val isVisible: Boolean) : CreateCampAction

    data object SubmitCamp : CreateCampAction

}