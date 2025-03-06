package com.example.graphqlandroid.domain.mapper.students

import com.example.FetchStudentsQuery
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.students.AppStudent
import database.AppStudentEntity

fun FetchStudentsQuery.Student.toAppStudent(): AppStudent {
    return AppStudent(
        age = age,
        camp = AppCamp(
            id = camp?.id ?: campId,
            name = camp?.name ?: "",
            startDate = "",
            endDate = "",
            curriculum = "",
            schoolId = camp?.schoolId ?: ""
        ),
        campId = campId,
        firstName = firstName,
        grade = grade,
        id = id,
        lastName = lastName
    )
}


fun AppStudentEntity.toAppStudents(): AppStudent {
    return AppStudent(
        age = age.toInt(),
        camp = AppCamp(
            id = campId,
            name = campName,
            startDate = "",
            endDate = "",
            curriculum = "",
            schoolId = schoolId
        ),
        campId = campId,
        firstName = firstName,
        grade = grade.toInt(),
        id = id,
        lastName = lastName
    )
}