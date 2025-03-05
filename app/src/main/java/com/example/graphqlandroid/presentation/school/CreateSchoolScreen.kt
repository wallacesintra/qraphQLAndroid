package com.example.graphqlandroid.presentation.school

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.domain.actions.CreateSchoolAction
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.schools.IndividualSchoolViewModel
import com.example.graphqlandroid.presentation.common.AppButton
import com.example.graphqlandroid.presentation.common.AppButtonLoadingContent
import com.example.graphqlandroid.presentation.common.AppTextField
import com.example.graphqlandroid.presentation.common.AppTopBar
import com.example.graphqlandroid.presentation.common.SnackBarContent
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSchoolScreen(modifier: Modifier = Modifier) {

    val individualSchoolViewModel = koinViewModel<IndividualSchoolViewModel>()
    val createSchoolForm = individualSchoolViewModel.createSchoolForm
    val countyList by individualSchoolViewModel.countiesListStateFlow.collectAsState()
    val organizationList by individualSchoolViewModel.organizationListStateFlow.collectAsState()
    val createSchoolResponseState by individualSchoolViewModel.createSchoolResponseStateFlow.collectAsState()

    LaunchedEffect(true) {
        individualSchoolViewModel.fetchData()
    }

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
        topBar = {
            AppTopBar(title = "Create School")
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) { innerPadding ->

        if (createSchoolForm.showBottomSheet) {
            ModalBottomSheet(
                sheetState = rememberModalBottomSheetState(),
                onDismissRequest = {
                    individualSchoolViewModel.onCreateSchoolAction(
                        createSchoolAction = CreateSchoolAction.OnShowBottomSheet(show = false)
                    )
                }
            ) {
                if (createSchoolForm.isSelectingCounty) {
                    countyList.data?.let { counties ->
                        if (counties.isEmpty()) {
                            Text(text = "No counties found")
                            return@ModalBottomSheet
                        }

                        LazyColumn {
                            items(counties, key = { it.id }) { county ->
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    onClick = {
                                        individualSchoolViewModel.onCreateSchoolAction(
                                            createSchoolAction = CreateSchoolAction.OnCountyChange(
                                                county = county
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = county.name)
                                }
                            }
                        }
                    }
                }


                if (createSchoolForm.isSelectingOrganization) {
                    organizationList.data?.let { organizations ->
                        if (organizations.isEmpty()) {
                            Text(text = "No organizations found")
                            return@ModalBottomSheet
                        }

                        LazyColumn {
                            items(organizations, key = { it.id }) { organization ->
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    onClick = {
                                        individualSchoolViewModel.onCreateSchoolAction(
                                            createSchoolAction = CreateSchoolAction.OnOrganizationChange(
                                                organization = organization
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = organization.name)
                                }
                            }
                        }
                    }
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {

            item {
                AppTextField(
                    label = "School Name",
                    value = createSchoolForm.name,
                    placeholder = "Enter school name",
                    onValueChanged = { name ->
                        individualSchoolViewModel.onCreateSchoolAction(
                            createSchoolAction = CreateSchoolAction.OnSchoolNameChange(name)
                        )
                    },
                    error = createSchoolForm.nameError
                )
            }

            item {
                AppTextField(
                    label = "County",
                    value = createSchoolForm.county?.name ?: "",
                    placeholder = "Enter county",
                    onValueChanged = {},
                    error = createSchoolForm.countyError,
                    enabled = false,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                individualSchoolViewModel.onCreateSchoolAction(
                                    createSchoolAction = CreateSchoolAction.OnShowCountyList(show = !createSchoolForm.isSelectingCounty)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Show county list"
                            )
                        }
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                individualSchoolViewModel.onCreateSchoolAction(
                                    createSchoolAction = CreateSchoolAction.OnShowCountyList(show = !createSchoolForm.isSelectingCounty)
                                )
                            }
                        )
                )
            }

            item {
                AppTextField(
                    label = "Organization",
                    value = createSchoolForm.organization?.name ?: "",
                    placeholder = "Enter organization",
                    onValueChanged = {},
                    error = createSchoolForm.organizationError,
                    enabled = false,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                individualSchoolViewModel.onCreateSchoolAction(
                                    createSchoolAction = CreateSchoolAction.OnShowOrganizationList(show = !createSchoolForm.isSelectingOrganization)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Show organization list"
                            )
                        }
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                individualSchoolViewModel.onCreateSchoolAction(
                                    createSchoolAction = CreateSchoolAction.OnShowOrganizationList(show = !createSchoolForm.isSelectingOrganization)
                                )
                            }
                        )
                )
            }

            item{
                AppButton(
                    onClick = {
                        individualSchoolViewModel.onCreateSchoolAction(
                            createSchoolAction = CreateSchoolAction.Submit
                        )
                    }
                ) {
                    when(createSchoolResponseState.status){
                        ResultStatus.LOADING -> {
                            AppButtonLoadingContent()
                        }
                        ResultStatus.INITIAL,
                        ResultStatus.SUCCESS ,
                        ResultStatus.ERROR -> {
                            Text(text = "Create School")
                        }
                    }
                }
            }

        }
    }
}