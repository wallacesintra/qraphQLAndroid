package com.example.graphqlandroid.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remoteRepository: RemoteRepository,
    private val databaseSource: DatabaseSource
): ViewModel() {

    init {
        fetchUser()
    }

    val userStateFlow = MutableStateFlow(Results.initial<User?>())

    private fun fetchUser(){
        viewModelScope.launch {
//            userStateFlow.value = Results.loading()
            databaseSource.getLoggedInUser()
                .catch { userStateFlow.value = Results.error() }
                .collect{ user -> userStateFlow.value = Results.success(data = user) }
        }
    }
}