package com.example.graphqlandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.graphqlandroid.presentation.FirstScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = FirstPage
    ){
        composable<FirstPage>{
            FirstScreen()
        }
    }
}