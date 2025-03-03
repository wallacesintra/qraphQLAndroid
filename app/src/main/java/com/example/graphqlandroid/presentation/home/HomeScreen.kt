package com.example.graphqlandroid.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.HomeViewModel
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    val userState by homeViewModel.userStateFlow.collectAsState()

    Scaffold(
        topBar = {
            Text(
                "Home",
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {innerPadding ->
        AnimatedContent(
            targetState = userState.status,
            modifier = Modifier
                .padding(innerPadding)

        ) { targetState ->
            when(targetState){
                ResultStatus.INITIAL,
                ResultStatus.LOADING -> {AppCircularLoading()}
                ResultStatus.SUCCESS -> {}
                ResultStatus.ERROR -> {}
            }
        }
    }
}