package com.example.graphqlandroid.domain.models

enum class AppCurriculum(
    val value: String,
    val text: String
) {
    LITERACY("LITERACY", "Literacy"),
    NUMERACY("NUMERACY", "Numeracy"),
    UNKNOWN("UNKNOWN", "Unknown");
}