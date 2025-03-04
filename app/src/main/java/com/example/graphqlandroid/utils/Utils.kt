package com.example.graphqlandroid.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
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

}