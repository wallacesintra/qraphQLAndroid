package com.example.graphqlandroid.domain.viewmodels.schools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.actions.CreateSchoolAction
import com.example.graphqlandroid.domain.dto.school.CreateSchool
import com.example.graphqlandroid.domain.mapper.school.toAppSchool
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.SnackBarItem
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import com.example.graphqlandroid.domain.states.CreateSchoolFormState
import com.example.graphqlandroid.domain.states.CreateSchoolFormState.Companion.toCreateSchoolDTO
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import com.example.graphqlandroid.utils.ValidateInput
import com.example.graphqlandroid.utils.ValidateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class IndividualSchoolViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource,
    private val snackBarHandler: SnackBarHandler
): ViewModel() {

    val schoolStateFlow = MutableStateFlow(Results.initial<DetailedSchool>())

    private fun fetchDetailedSchoolInfo(schoolId: String){
        viewModelScope.launch {
            remoteRepository.fetchDetailedSchoolInfo(schoolId = schoolId)
                .collect{response ->
                    response.data?.let { school ->
                        databaseSource.insertDetailedSchoolInfo(detailedSchool = school )
                    }
                }
        }
    }

    private fun getDetailedSchoolInfo(schoolId: String){
        viewModelScope.launch {
            databaseSource.getDetailedSchoolInfo(schoolId = schoolId)
                .catch { schoolStateFlow.value = Results.error() }
                .collect{school -> schoolStateFlow.value = Results.success(data = school)}
        }
    }

    fun refresh(school_id: String){
        fetchDetailedSchoolInfo(schoolId = school_id)
        getDetailedSchoolInfo(schoolId = school_id)
    }

    var createSchoolForm by mutableStateOf(CreateSchoolFormState())

    private val validateInput = ValidateInput()
    private val validateString = ValidateString()

    val countiesListStateFlow = MutableStateFlow(Results.initial<List<AppCounty>>())
    val organizationListStateFlow = MutableStateFlow(Results.initial<List<AppOrganization>>())

    val createSchoolResponseStateFlow = MutableStateFlow(Results.initial<CreateSchool>())

    private fun fetchCounties(){
        viewModelScope.launch {
            remoteRepository.fetchCounties()
                .collect { response ->
                    response.data?.let { appCounties ->
                        appCounties.forEach { appCounty ->
                            appCounty?.let { databaseSource.insertCounty(appCounty = appCounty) }
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


    private fun getCounties(){
        viewModelScope.launch {
            databaseSource.getCounties()
                .catch { countiesListStateFlow.value = Results.error() }
                .collect { counties -> countiesListStateFlow.value = Results.success(data = counties) }
        }
    }

    private fun getOrganizations(){
        viewModelScope.launch {
            databaseSource.getOrganizations()
                .catch { organizationListStateFlow.value = Results.error() }
                .collect { organizations -> organizationListStateFlow.value = Results.success(data = organizations) }
        }
    }

    fun fetchData(){
        fetchOrganizations()
        fetchCounties()
        getOrganizations()
        getCounties()
    }

    fun onCreateSchoolAction(createSchoolAction: CreateSchoolAction){
        when(createSchoolAction){

            is CreateSchoolAction.OnCountyChange -> {
                createSchoolForm = createSchoolForm.copy(
                    county = createSchoolAction.county,
                    showBottomSheet = false,
                    isSelectingOrganization = false,
                    isSelectingCounty = false
                )
            }

            is CreateSchoolAction.OnOrganizationChange -> {
                createSchoolForm = createSchoolForm.copy(
                    organization = createSchoolAction.organization,
                    showBottomSheet = false,
                    isSelectingOrganization = false,
                    isSelectingCounty = false
                )
            }

            is CreateSchoolAction.OnSchoolNameChange -> {
                createSchoolForm = createSchoolForm.copy(
                    name = createSchoolAction.schoolName
                )
            }
            is CreateSchoolAction.OnShowCountyList -> {
                createSchoolForm = createSchoolForm.copy(
                    isSelectingCounty = createSchoolAction.show,
                    showBottomSheet = createSchoolAction.show
                )
            }
            is CreateSchoolAction.OnShowOrganizationList -> {
                createSchoolForm = createSchoolForm.copy(
                    isSelectingOrganization = createSchoolAction.show,
                    showBottomSheet = createSchoolAction.show
                )
            }

            is CreateSchoolAction.OnShowBottomSheet -> {
                createSchoolForm = createSchoolForm.copy(
                    showBottomSheet = createSchoolAction.show,
                    isSelectingOrganization = createSchoolAction.show,
                    isSelectingCounty = createSchoolAction.show
                )
            }


            CreateSchoolAction.Submit -> {
                createSchool()
            }

        }

        }

    private fun createSchool() {
        val nameResult = validateString.execute(text = createSchoolForm.name)
        val countyResult = validateInput.execute(input = createSchoolForm.county)
        val organizationResult = validateInput.execute(input = createSchoolForm.organization)

        val hasError = listOf(
            nameResult,
            countyResult,
            organizationResult
        ).any { !it.isSuccessful }

        if (hasError){
            createSchoolForm = createSchoolForm.copy(
                nameError = nameResult.errorMessage,
                countyError = countyResult.errorMessage,
                organizationError = organizationResult.errorMessage
            )
            return
        }

        createSchoolForm = createSchoolForm.copy(
            nameError = nameResult.errorMessage,
            countyError = countyResult.errorMessage,
            organizationError = organizationResult.errorMessage
        )

        viewModelScope.launch {
            remoteRepository.createSchool(createSchoolForm.toCreateSchoolDTO())
                .collect{ response ->

                    createSchoolResponseStateFlow.value = response

                    response.data?.let {
                        databaseSource.insertSchool(appSchool = response.data.toAppSchool())

                        createSchoolForm = createSchoolForm.copy(
                            name = "",
                            county = null,
                            organization = null
                        )
                    }

                    snackBarHandler.showSnackBarNotification(
                        SnackBarItem(
                            message = if (response.status == ResultStatus.SUCCESS) "School created successfully" else "Error creating school",
                            isError = response.status != ResultStatus.SUCCESS
                        )
                    )
                }
        }
    }
}

