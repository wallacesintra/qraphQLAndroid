package com.example.graphqlandroid.presentation.home

import androidx.compose.runtime.Composable
import com.example.graphqlandroid.R
import com.example.graphqlandroid.presentation.dashboard.DashboardScreen
import com.example.graphqlandroid.presentation.school.SchoolListScreen

enum class AppScreen(
    val screen: @Composable () -> Unit,
    val topBarTitle: String = "",
    val bottomBarTitle: String,
    val icon:   Int
) {
    Home(
        screen = { DashboardScreen() },
        bottomBarTitle = "Home",
        icon = R.drawable.dashboard_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),
    School(
        screen = { SchoolListScreen()},
        topBarTitle = "My Schools",
        bottomBarTitle = "Schools",
        icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz24_1_
    ),
    Students(
        screen = {},
        topBarTitle = "My Students",
        bottomBarTitle = "Students",
        icon = R.drawable.group_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),
    Camp(
        screen = {},
        topBarTitle = "Camps",
        bottomBarTitle = "AppCamp",
        icon = R.drawable.camping_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),

}