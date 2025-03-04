package com.example.graphqlandroid.domain.viewmodels.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.Results
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

    private fun getUser(){
        viewModelScope.launch {
            databaseSource.getLoggedInUser()
                .catch { userStateFlow.value = Results.error() }
                .collect{ user -> userStateFlow.value = Results.success(data = user) }
        }
    }


    fun refresh(){
        fetchCountAggregrate()
        getCountAggregrate()
        getUser()
    }

}