package com.example.graphqlandroid.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.actions.LoginAction
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.authentication.LoginViewModel
import com.example.graphqlandroid.presentation.common.AppButton
import com.example.graphqlandroid.presentation.common.AppButtonLoadingContent
import com.example.graphqlandroid.presentation.common.AppTextField
import com.example.graphqlandroid.presentation.common.SnackBarContent
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    val loginViewModel = koinViewModel<LoginViewModel>()
    val loginResponseState by loginViewModel.loginResponseStateFlow.collectAsState()
    val loginForm = loginViewModel.loginFormSate

    val snackBarHandler = koinInject<SnackBarHandler>()
    val snackBarNotification by snackBarHandler.snackBarNotification.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackBarNotification.status) {
        if (snackBarNotification.status == ResultStatus.SUCCESS) {
            snackBarHostState.showSnackbar(
                duration = SnackbarDuration.Long,
                message = snackBarNotification.data?.message ?: "",
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackBarContent(
                modifier = Modifier.statusBarsPadding(),
                snackBarHostState = snackBarHostState,
                snackBarItem = snackBarNotification.data,
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.security),
                    contentDescription = "Security",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillParentMaxHeight(0.18f),
                )
            }

            item {
                Text(
                    text = "Login",
                    modifier = Modifier.fillMaxWidth(),
                    style =
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                        ),
                )
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Hello again! Ready to pick up where you left off?",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                )
            }

            item {
                AppTextField(
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    value = loginForm.email,
                    placeholder = "johndoe@gmail.com",
                    error = loginForm.emailError,
                    onValueChanged = { email ->
                        loginViewModel.onLoginActions(
                            loginAction = LoginAction.OnEmailChange(email = email)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
            }

            item {
                AppTextField(
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    value = loginForm.password,
                    placeholder = "**********",
                    passwordVisible = loginForm.isPasswordVisible,
                    error = loginForm.passwordError,
                    onValueChanged = { password ->
                        loginViewModel.onLoginActions(
                            loginAction = LoginAction.OnPasswordChange(password = password)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                loginViewModel.onLoginActions(LoginAction.OnPasswordVisibilityChange(show = !loginForm.isPasswordVisible))
                            },
                        ) {
                            Icon(
                                painter = painterResource(if (loginForm.isPasswordVisible) R.drawable.visibility_off_24dp_fill0_wght400_grad0_opsz24 else R.drawable.visibility_24dp_fill0_wght400_grad0_opsz24),
                                contentDescription = null,
                            )
                        }
                    }
                )
            }

            item {
                AppButton(
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        loginViewModel.onLoginActions(LoginAction.OnSubmit)
                    },
                    content = {
                        when(loginResponseState.status){
                            ResultStatus.LOADING -> {
                                AppButtonLoadingContent()
                            }
                            ResultStatus.INITIAL,
                            ResultStatus.SUCCESS,
                            ResultStatus.ERROR -> {
                                Text(
                                    "Login"
                                )
                            }
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth(),

                )

            }
        }
    }
}