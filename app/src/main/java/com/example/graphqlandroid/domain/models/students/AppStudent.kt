package com.example.graphqlandroid.domain.models.students

import com.example.graphqlandroid.domain.models.school.AppCamp

data class AppStudent(
    val age: Int,
    val camp: AppCamp,
    val campId: String,
    val firstName: String,
    val grade: Int,
    val id: String,
    val lastName: String
)