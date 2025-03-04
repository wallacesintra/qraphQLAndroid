package com.example.graphqlandroid.domain.viewmodels.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.AppUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FirstPageViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    val appUserStateFlow = MutableStateFlow(Results.initial<AppUser?>())

    init {
        fetchLoggedInUser()
    }

    private fun fetchLoggedInUser(){
        viewModelScope.launch {
            appUserStateFlow.value = Results.loading()
            databaseSource.getLoggedInUser()
                .catch { appUserStateFlow.value = Results.error() }
                .collect{user -> appUserStateFlow.value = Results.success(data = user)}
        }
    }
}