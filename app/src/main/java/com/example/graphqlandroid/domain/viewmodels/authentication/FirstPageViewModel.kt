package com.example.graphqlandroid.domain.viewmodels.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FirstPageViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    val userStateFlow = MutableStateFlow(Results.initial<User?>())

    init {
        fetchLoggedInUser()
    }

    private fun fetchLoggedInUser(){
        viewModelScope.launch {
            userStateFlow.value = Results.loading()
            databaseSource.getLoggedInUser()
                .catch { userStateFlow.value = Results.error() }
                .collect{user -> userStateFlow.value = Results.success(data = user)}
        }
    }
}