package com.example.graphqlandroid.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object FirstPage

@Serializable
object HomePage

@Serializable
data class SchoolPage(val id: String)