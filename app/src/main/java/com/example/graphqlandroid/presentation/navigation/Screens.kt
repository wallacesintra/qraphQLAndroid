package com.example.graphqlandroid.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object FirstPage

@Serializable
object HomePage

@Serializable
data class SchoolPage(val id: String)

@Serializable
data object CreateSchoolPage

@Serializable
data object CreateCampPage

@Serializable
data class CampPage(val id: String)