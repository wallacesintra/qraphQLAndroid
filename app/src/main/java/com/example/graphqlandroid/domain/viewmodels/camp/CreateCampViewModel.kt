package com.example.graphqlandroid.domain.viewmodels.camp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.actions.CreateCampAction
import com.example.graphqlandroid.domain.dto.camp.CreateCamp
import com.example.graphqlandroid.domain.mapper.toAppCamp
import com.example.graphqlandroid.domain.models.AppCurriculum
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.SnackBarItem
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.states.CreateCampFormState
import com.example.graphqlandroid.domain.states.CreateCampFormState.Companion.toCreateCampRequestDTO
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import com.example.graphqlandroid.utils.ValidateInput
import com.example.graphqlandroid.utils.ValidateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateCampViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource,
    private val snackBarHandler: SnackBarHandler
):ViewModel(){

    init {

        fetchOrganizations()
        fetchSchools()

        getSchools()
        getOrganizations()

    }

    var createCampForm by mutableStateOf(CreateCampFormState())

    val createCampResponseStateFlow = MutableStateFlow(Results.initial<CreateCamp>())

    val schoolListStateFlow = MutableStateFlow(Results.initial<List<AppSchool>>())
    val organizationListStateFlow = MutableStateFlow(Results.initial<List<AppOrganization>>())

    val curriculumList by mutableStateOf(AppCurriculum.entries.filter {  it != AppCurriculum.UNKNOWN})

    private fun fetchSchools(){
        viewModelScope.launch {
            remoteRepository.fetchSchools()
                .collect{ response ->

                    response.data?.let { schools ->
                        schools.forEach { appSchool ->
                            appSchool?.let { databaseSource.insertSchool(appSchool) }
                        }
                    }
                }
        }
    }

    private fun fetchOrganizations(){
        viewModelScope.launch {
            remoteRepository.fetchOrganization()
                .collect { response ->
                    response.data?.let { appOrganizations ->
                        appOrganizations.forEach { appOrganization ->
                            appOrganization?.let { databaseSource.insertOrganization(appOrganization = appOrganization) }
                        }
                    }
                }
        }
    }

    private fun getSchools(){
        viewModelScope.launch {
            databaseSource.getSchools()
                .catch { schoolListStateFlow.value = Results.error() }
                .collect{ schools -> schoolListStateFlow.value = Results.success(data = schools)}
        }
    }

    private fun getOrganizations(){
        viewModelScope.launch {
            databaseSource.getOrganizations()
                .catch { organizationListStateFlow.value = Results.error() }
                .collect { organizations -> organizationListStateFlow.value = Results.success(data = organizations) }
        }
    }

    fun onCreateCampAction(createCampAction: CreateCampAction){
        when(createCampAction){
            is CreateCampAction.IsStartDatePickerVisible -> {
                createCampForm = createCampForm.copy(
                    showStartDatePicker = createCampAction.isVisible
                )
            }
            is CreateCampAction.OnCurriculumChange -> {
                createCampForm = createCampForm.copy(
                    curriculum = createCampAction.curriculum,
                    showCurriculumList = false
                )
            }
            is CreateCampAction.OnEndDateChange -> {
                createCampForm = createCampForm.copy(
                    endDate = createCampAction.endDate,
                    showEndDatePicker = false
                )
            }
            is CreateCampAction.OnNameChange -> {
                createCampForm = createCampForm.copy(
                    name = createCampAction.name
                )
            }
            is CreateCampAction.OnOrganizationIdChange -> {
                createCampForm = createCampForm.copy(
                    organization = createCampAction.organizationId,
                    showOrganizationList = false
                )
            }
            is CreateCampAction.OnSchoolChange -> {
                createCampForm = createCampForm.copy(
                    school = createCampAction.schoolId,
                    showSchoolList = false
                )
            }
            is CreateCampAction.OnStartDateChange -> {
                createCampForm = createCampForm.copy(
                    startDate = createCampAction.startDate,
                    showStartDatePicker = false
                )
            }
            is CreateCampAction.IsEndDatePickerVisible -> {
                createCampForm = createCampForm.copy(
                    showEndDatePicker = createCampAction.isVisible
                )
            }

            is CreateCampAction.IsCurriculumPickerVisible -> {
                createCampForm = createCampForm.copy(
                    showCurriculumList = createCampAction.isVisible
                )
            }
            is CreateCampAction.IsSchoolPickerVisible -> {
                createCampForm = createCampForm.copy(
                    showSchoolList = createCampAction.isVisible
                )
            }

            is CreateCampAction.IsOrganizationPickerVisible -> {
                createCampForm = createCampForm.copy(
                    showOrganizationList = createCampAction.isVisible
                )
            }


            is CreateCampAction.OnShowBottomSheetChange -> {
                createCampForm = createCampForm.copy(
                    showOrganizationList = false,
                    showCurriculumList = false,
                    showSchoolList = false
                )
            }

            CreateCampAction.SubmitCamp -> {createCamp()}
        }
    }

    private val validateInput = ValidateInput()

    private val validateString = ValidateString()

    private fun createCamp() {

        val nameResult = validateString.execute(createCampForm.name)
        val startDateResult = validateInput.execute(createCampForm.startDate)
        val endDateResult = validateInput.execute(createCampForm.endDate)
        val curriculumResult = validateInput.execute(createCampForm.curriculum)
        val organizationResult = validateInput.execute(createCampForm.organization)
        val schoolResult = validateInput.execute(createCampForm.school)

        val hasError = listOf(
            nameResult,
            startDateResult,
            endDateResult,
            curriculumResult,
            organizationResult,
            schoolResult
        ).any { !it.isSuccessful }

        if (hasError) {
            createCampForm = createCampForm.copy(
                nameError = nameResult.errorMessage,
                startDateError = startDateResult.errorMessage,
                endDateError = endDateResult.errorMessage,
                curriculumError = curriculumResult.errorMessage,
                organizationError = organizationResult.errorMessage,
                schoolError = schoolResult.errorMessage
            )
            return
        }

        createCampForm = createCampForm.copy(
            nameError = nameResult.errorMessage,
            startDateError = startDateResult.errorMessage,
            endDateError = endDateResult.errorMessage,
            curriculumError = curriculumResult.errorMessage,
            organizationError = organizationResult.errorMessage,
            schoolError = schoolResult.errorMessage
        )

        val createCampRequestDTO = createCampForm.toCreateCampRequestDTO()

        Log.d("request data ", createCampRequestDTO.toString())


        viewModelScope.launch {
            remoteRepository.createCamp(createCampForm.toCreateCampRequestDTO())
                .collect{response ->
                    createCampResponseStateFlow.value = response

                    response.data?.let {

                        databaseSource.insertCamp(appCamp = response.data.toAppCamp())

                        createCampForm  = createCampForm.copy(
                            name = "",
                            startDate = null,
                            endDate =null,
                            curriculum = null,
                            organization = null,
                            school = null
                        )

                    }

                    snackBarHandler.showSnackBarNotification(
                        SnackBarItem(
                            message = if (response.status == ResultStatus.SUCCESS) "School created successfully" else "Error creating school",
                            isError = response.status != ResultStatus.SUCCESS,
                        )
                    )
                }
        }
    }

}
