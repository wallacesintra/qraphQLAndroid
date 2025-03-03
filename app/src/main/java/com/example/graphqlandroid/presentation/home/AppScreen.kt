package com.example.graphqlandroid.presentation.home

import androidx.compose.runtime.Composable
import com.example.graphqlandroid.R
import com.example.graphqlandroid.presentation.dashboard.DashboardScreen
import com.example.graphqlandroid.presentation.school.SchoolScreen

enum class AppScreen(
    val screen: @Composable () -> Unit,
    val title: String,
    val icon:   Int
) {
    Home(
        screen = { DashboardScreen() },
        title = "Home",
        icon = R.drawable.dashboard_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),
    School(
        screen = { SchoolScreen()},
        title = "School",
        icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz24_1_
    ),
    Students(
        screen = {},
        title = "Students",
        icon = R.drawable.group_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),
    Camp(
        screen = {},
        title = "Camp",
        icon = R.drawable.camping_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),

}