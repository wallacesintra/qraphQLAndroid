package com.example.graphqlandroid.domain.viewmodels.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.students.AppStudent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    init {
        refresh()
    }

    val countStateFlow = MutableStateFlow(Results.initial<CountAggregrate>())
    val userStateFlow = MutableStateFlow(Results.initial<AppUser>())

    val studentsList = MutableStateFlow(Results.initial<List<AppStudent>>())
    val campsListStateFlow = MutableStateFlow(Results.initial<List<AppCamp>>())


    private fun fetchCountAggregrate(){
        viewModelScope.launch {
            remoteRepository.fetchCountAggregrate()
                .collect{ response ->
                    response.data?.let { data ->
                        databaseSource.insertCount(countAggregrate = data)
                    }
                }
        }
    }


    private fun getCountAggregrate(){
        viewModelScope.launch {
            databaseSource.getCountAggregrate()
                .catch { countStateFlow.value = Results.error() }
                .collect{count -> countStateFlow.value = Results.success(data = count)}
        }
    }


    private fun fetchStudents() {
        viewModelScope.launch {
            remoteRepository.fetchStudents()
                .collect { response ->
                    response.data?.let { data ->
                        data.forEach { student ->
                            student?.let { databaseSource.insertStudent(appStudent = student) }
                        }
                    }
                }
        }
    }

    private fun getStudents() {
        viewModelScope.launch {
            databaseSource.getStudents()
                .catch { studentsList.value = Results.error() }
                .collect { students -> studentsList.value = Results.success(data = students.take(4)) }
        }
    }


    private fun fetchCamps(){
        viewModelScope.launch {
            remoteRepository.fetchCamps()
                .collect { response ->
                    response.data?.let { data ->
                        data.forEach { camp ->
                            camp?.let { databaseSource.insertCamp(appCamp = camp) }
                        }
                    }
                }
        }
    }

    private fun getCamps(){
        viewModelScope.launch {
            databaseSource.getCamps()
                .catch { campsListStateFlow.value = Results.error() }
                .collect { camps -> campsListStateFlow.value = Results.success(data = camps.take(4)) }
        }
    }

    private fun getUser(){
        viewModelScope.launch {
            databaseSource.getLoggedInUser()
                .catch { userStateFlow.value = Results.error() }
                .collect{ user -> userStateFlow.value = Results.success(data = user) }
        }
    }


    fun refresh(){
        fetchCountAggregrate()
        fetchStudents()
        fetchCamps()

        getCountAggregrate()
        getUser()
        getStudents()
        getCamps()
    }

}