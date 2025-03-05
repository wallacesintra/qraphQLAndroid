package com.example.graphqlandroid.presentation.home

import androidx.compose.runtime.Composable
import com.example.graphqlandroid.R
import com.example.graphqlandroid.navController
import com.example.graphqlandroid.presentation.camp.CampListScreen
import com.example.graphqlandroid.presentation.dashboard.DashboardScreen
import com.example.graphqlandroid.presentation.navigation.CreateCampPage
import com.example.graphqlandroid.presentation.navigation.CreateSchoolPage
import com.example.graphqlandroid.presentation.school.CreateSchoolScreen
import com.example.graphqlandroid.presentation.school.SchoolListScreen

enum class AppScreen(
    val screen: @Composable () -> Unit,
    val addAction:  () -> Unit = {},
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
        addAction = { navController.navigate(CreateSchoolPage)},
        topBarTitle = "My Schools",
        bottomBarTitle = "Schools",
        icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz24_1_
    ),
    Camp(
        screen = {CampListScreen()},
        addAction = { navController.navigate(CreateCampPage)},
        topBarTitle = "Camps",
        bottomBarTitle = "Camps",
        icon = R.drawable.camping_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),
    Students(
        screen = {},
        topBarTitle = "My Students",
        bottomBarTitle = "Students",
        icon = R.drawable.group_24dp_e8eaed_fill0_wght400_grad0_opsz24
    ),


}