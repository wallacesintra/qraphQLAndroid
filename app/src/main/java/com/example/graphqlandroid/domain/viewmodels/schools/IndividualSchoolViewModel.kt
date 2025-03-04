package com.example.graphqlandroid.domain.viewmodels.schools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class IndividualSchoolViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
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
}