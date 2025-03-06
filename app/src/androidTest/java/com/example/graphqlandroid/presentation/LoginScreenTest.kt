package com.example.graphqlandroid.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.graphqlandroid.presentation.authentication.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun login_button_is_clickable_when_all_input_are_filled() {
        // Start the app
        composeTestRule.setContent {
            LoginScreen()
        }


        composeTestRule.onNodeWithTag("LoginButton").assertExists()
        composeTestRule.onNodeWithTag("LoginButton").performClick()

        composeTestRule.onNodeWithTag("EmailTextField").performTextInput("wallace@gmail.com")
        composeTestRule.onNodeWithTag("PasswordTextField").performTextInput("123456")

        composeTestRule.onNodeWithTag("LoginButton").performClick()

    }
}