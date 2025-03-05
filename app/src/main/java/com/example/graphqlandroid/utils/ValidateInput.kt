package com.example.graphqlandroid.utils

class ValidateInput {
    fun <T> execute(input: T?): ValidationResult {
        if (input == null) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "required",
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}
