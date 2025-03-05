package com.example.graphqlandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.example.graphqlandroid.presentation.FirstScreen
import com.example.graphqlandroid.presentation.home.HomeScreen
import com.example.graphqlandroid.presentation.school.CreateSchoolScreen
import com.example.graphqlandroid.presentation.school.SchoolScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = FirstPage
    ){
        composable<FirstPage>{
            FirstScreen()
        }

        composable<HomePage> {
            HomeScreen()
        }

        composable<SchoolPage> { backStackEntry ->
            val args = backStackEntry.toRoute<SchoolPage>()

            SchoolScreen(schoolId = args.id)
        }

        composable<CreateSchoolPage> {
            CreateSchoolScreen()
        }
    }
}