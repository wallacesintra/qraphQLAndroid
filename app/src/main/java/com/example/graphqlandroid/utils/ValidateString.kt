package com.example.graphqlandroid.utils

class ValidateString {

    fun execute(text: String?): ValidationResult {
        if (text.isNullOrBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "It can't be empty"
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }

}