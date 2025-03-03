package com.example.graphqlandroid.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.presentation.home.AppScreen
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

    var currentScreen by mutableStateOf(AppScreen.Home)

    fun updateScreen(screen: AppScreen){
        currentScreen = screen
    }

    val appUserStateFlow = MutableStateFlow(Results.initial<AppUser?>())

    private fun fetchUser(){
        viewModelScope.launch {
//            appUserStateFlow.value = Results.loading()
            databaseSource.getLoggedInUser()
                .catch { appUserStateFlow.value = Results.error() }
                .collect{ user -> appUserStateFlow.value = Results.success(data = user) }
        }
    }
}