package com.example.graphqlandroid.domain.viewmodels.schools

import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.school.AppSchool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SchoolViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    init {
        refresh()
    }

    val schoolsStateFlow = MutableStateFlow(Results.initial<List<AppSchool>>())

    private fun fetchSchools(){
        viewModelScope.launch {
            remoteRepository.fetchSchools()
                .flowOn(Dispatchers.IO)
                .collect{ response ->
                    response.data?.let { appSchools ->

                        databaseSource.deleteSchools()

                        appSchools.forEach { appSchool ->
                            if (appSchool != null) {
                                databaseSource.insertSchool(appSchool)
                            }
                        }
                    }
                }
        }
    }

    private fun getSchools(){
        viewModelScope.launch {
            databaseSource.getSchools()
                .catch { schoolsStateFlow.value = Results.error() }
                .collect{schools -> schoolsStateFlow.value = Results.success(data = schools) }
        }
    }

    fun refresh(){
        fetchSchools()
        getSchools()
    }
}