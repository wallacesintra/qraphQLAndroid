package com.example.graphqlandroid.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.HomeViewModel
import com.example.graphqlandroid.navController
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.navigation.CreateSchoolPage
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    val userState by homeViewModel.appUserStateFlow.collectAsState()
    val currentScreen = homeViewModel.currentScreen

    Scaffold(
        topBar = {
            Text(
                currentScreen.topBarTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(12.dp)
            )
        },
        bottomBar = {
            BottomAppBar {
                AppScreen.entries.forEach{screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = {
                            homeViewModel.updateScreen(screen)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(screen.icon),
                                contentDescription = screen.bottomBarTitle
                            )
                        },
                        modifier = Modifier,
                        enabled = true,
                        label = {
                            Text(
                                text = screen.bottomBarTitle
                            )
                        },
                        alwaysShowLabel = true,
                    )
                }
            }
        },
        floatingActionButton = {
            when(currentScreen){
                AppScreen.Home -> {}
                else ->
                    FloatingActionButton(
                        onClick = currentScreen.addAction
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add ${currentScreen.bottomBarTitle}"
                        )
                    }
            }
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
                ResultStatus.SUCCESS -> {
                    currentScreen.screen.invoke()
                }
                ResultStatus.ERROR -> {}
            }
        }
    }
}