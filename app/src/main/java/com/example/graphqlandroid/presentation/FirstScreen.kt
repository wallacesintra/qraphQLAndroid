package com.example.graphqlandroid.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.authentication.FirstPageViewModel
import com.example.graphqlandroid.presentation.authentication.LoginScreen
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun FirstScreen(){

    val firstPageViewModel = koinViewModel<FirstPageViewModel>()
    val userState by firstPageViewModel.appUserStateFlow.collectAsState()

    AnimatedContent(
        targetState = userState.status
    ) { targetState ->
        when (targetState){
            ResultStatus.INITIAL,
            ResultStatus.LOADING -> {
                AppCircularLoading()
            }
            ResultStatus.SUCCESS -> {
                userState.data?.let {
                    HomeScreen()
                }?: LoginScreen()

//                HomeScreen()

            }
            ResultStatus.ERROR -> {}
        }

    }
}