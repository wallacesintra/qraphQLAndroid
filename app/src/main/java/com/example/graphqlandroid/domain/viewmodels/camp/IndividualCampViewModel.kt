package com.example.graphqlandroid.domain.viewmodels.camp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.camps.DetailedCampInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class IndividualCampViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    val campStateFlow = MutableStateFlow(Results.initial<DetailedCampInfo>())

    private fun fetchDetailedCampInfo(campId: String){
        viewModelScope.launch {
            remoteRepository.fetchDetailedCampInfo(campId = campId)
                .collect{ response ->
                    response.data?.let { camp ->
                        databaseSource.insertDetailedCampInfo(detailedCamp = camp)
                    }

                }
        }
    }


    private fun getDetailedCampInfo(campId: String){
        viewModelScope.launch {
            databaseSource.getDetailedCampById(campId = campId)
                .catch { campStateFlow.value = Results.error() }
                .collect{ camp -> campStateFlow.value = Results.success(data = camp)}
        }
    }

    fun refresh(campId: String){
        fetchDetailedCampInfo(campId = campId)
        getDetailedCampInfo(campId = campId)
    }
}