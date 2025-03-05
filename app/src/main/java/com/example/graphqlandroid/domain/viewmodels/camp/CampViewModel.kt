package com.example.graphqlandroid.domain.viewmodels.camp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.school.AppCamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CampViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    init {
        fetchCamps()
        getCamps()
    }

    val campStateFlow = MutableStateFlow(Results.initial<List<AppCamp>>())

    private fun fetchCamps(){

        viewModelScope.launch {
            remoteRepository.fetchCamps()
                .collect{response ->
                    response.data?.let { appCamps ->
                        appCamps.forEach { appCamp ->
                            appCamp?.let { databaseSource.insertCamp(appCamp = it) }
                        }
                    }

                }
        }

    }

    private fun getCamps(){
        viewModelScope.launch {
            databaseSource.getCamps()
                .catch { campStateFlow.value = Results.error() }
                .collect{camps -> campStateFlow.value = Results.success(data = camps)}
        }
    }

}