package com.example.graphqlandroid.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex)
    }

    @Composable
    fun animatedNumberString(number: String, animatedPlayed: Boolean): String {
        val numberInt = number.toIntOrNull()

        return numberInt?.let {
            animateIntAsState(
                targetValue = if (animatedPlayed) numberInt else 0,
                animationSpec = tween(
                    durationMillis = if (numberInt > 10) 4000 else 400,
                    delayMillis = 100
                ),
                label = "animated int string",
            ).value.toString()
        } ?: number
    }


    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }


    fun formatDateTimeToDateMonth(currentMoment: Instant): String {
        val currentDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

        val dayOfMonth = currentDateTime.dayOfMonth
        val month = currentDateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }

        return "$dayOfMonth $month"
    }

    fun convertInstantToString(instant: Instant?): String {
        val localDateTime = instant?.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime?.year}-${localDateTime?.monthNumber.toString().padStart(2, '0')}-${localDateTime?.dayOfMonth.toString().padStart(2, '0')}"
    }

}