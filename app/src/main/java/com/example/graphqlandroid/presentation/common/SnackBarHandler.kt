package com.example.graphqlandroid.presentation.common

import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.SnackBarItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SnackBarHandler {
    val snackBarNotification = MutableStateFlow(Results.initial<SnackBarItem>())

    fun showSnackBarNotification(snackBarItem: SnackBarItem) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(100L)
            snackBarNotification.value = Results.success(snackBarItem)
            delay(5000L)
            snackBarNotification.value = Results.initial()
        }
    }
}