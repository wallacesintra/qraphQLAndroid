package com.example.graphqlandroid.domain.mapper

import com.example.graphqlandroid.domain.models.AppCurriculum
import com.example.type.Curriculum

fun AppCurriculum.toCurriculum(): Curriculum {
    return when (this) {
        AppCurriculum.LITERACY -> Curriculum.LITERACY
        AppCurriculum.NUMERACY -> Curriculum.NUMERACY
        else -> Curriculum.UNKNOWN__
    }
}

fun Curriculum.toAppCurriculum(): AppCurriculum {
    return when (this) {
        Curriculum.LITERACY -> AppCurriculum.LITERACY
        Curriculum.NUMERACY -> AppCurriculum.NUMERACY
        else -> AppCurriculum.UNKNOWN
    }
}