package com.example.graphqlandroid.presentation.camp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.actions.CreateCampAction
import com.example.graphqlandroid.domain.actions.CreateSchoolAction
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.camp.CreateCampViewModel
import com.example.graphqlandroid.presentation.common.AppButton
import com.example.graphqlandroid.presentation.common.AppButtonLoadingContent
import com.example.graphqlandroid.presentation.common.AppTextField
import com.example.graphqlandroid.presentation.common.AppTopBar
import com.example.graphqlandroid.presentation.common.SnackBarContent
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import com.example.graphqlandroid.utils.Utils.formatDateTimeToDateMonth
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCampScreen(modifier: Modifier = Modifier) {

    val createCampViewModel = koinInject<CreateCampViewModel>()
    val schoolListState by createCampViewModel.schoolListStateFlow.collectAsState()
    val organizationListState by createCampViewModel.organizationListStateFlow.collectAsState()
    val createCampResponse by createCampViewModel.createCampResponseStateFlow.collectAsState()
    val curriculumList = createCampViewModel.curriculumList

    val createCampForm = createCampViewModel.createCampForm


    val snackBarHandler = koinInject<SnackBarHandler>()
    val snackBarNotification by snackBarHandler.snackBarNotification.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val datePickerState = rememberDatePickerState()

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
            AppTopBar(title = "Create Camp")
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) { innerPadding ->



        if (createCampForm.showSchoolList || createCampForm.showCurriculumList || createCampForm.showOrganizationList){

            ModalBottomSheet(
                onDismissRequest = {
                    createCampViewModel.onCreateCampAction(
                        CreateCampAction.OnShowBottomSheetChange(false)
                    )
                }
            ) {

                if (createCampForm.showSchoolList) {
                    schoolListState.data?.let { schools ->
                        if (schools.isEmpty()) {
                            Text(
                                text = "No schools found"
                            )
                            return@ModalBottomSheet
                        }

                        LazyColumn {
                            items(schools, key = { it.id }) { school ->
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    onClick = {
                                        createCampViewModel.onCreateCampAction(
                                            CreateCampAction.OnSchoolChange(school)
                                        )
                                    }
                                ) {
                                    Text(text = school.name)
                                }

                            }
                        }
                    }
                }

                if (createCampForm.showOrganizationList) {
                    organizationListState.data?.let { organizations ->
                        if (organizations.isEmpty()) {
                            Text(
                                text = "No organizations found"
                            )
                            return@ModalBottomSheet
                        }

                        LazyColumn {
                            items(organizations, key = { it.id }) { organization ->
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    onClick = {
                                        createCampViewModel.onCreateCampAction(
                                            CreateCampAction.OnOrganizationIdChange(organization)
                                        )
                                    }
                                ) {
                                    Text(text = organization.name)
                                }

                            }
                        }
                    }
                }

                if (createCampForm.showCurriculumList) {
                    if (curriculumList.isEmpty()) {
                        Text(
                            text = "No curriculum found"
                        )
                        return@ModalBottomSheet
                    }

                    LazyColumn {
                        items(curriculumList) { curriculum ->
                            TextButton(
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                ),
                                onClick = {
                                    createCampViewModel.onCreateCampAction(
                                        CreateCampAction.OnCurriculumChange(curriculum)
                                    )
                                }
                            ) {
                                Text(text = curriculum.text)
                            }

                        }
                    }
                }
            }
        }


        if (createCampForm.showStartDatePicker){
            DatePickerDialog(
                onDismissRequest = {
                    createCampViewModel.onCreateCampAction(
                        CreateCampAction.IsStartDatePickerVisible(false)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            createCampViewModel.onCreateCampAction(
                                CreateCampAction.OnStartDateChange(datePickerState.selectedDateMillis?.let { Instant.fromEpochMilliseconds(it) }!!)
                            )

                        },
                        content = {
                            Text(
                                text = "Confirm"
                            )
                        }
                    )
                },
                content = {
                    DatePicker(
                        title = {
                            Text(
                                text = "Select start date",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(12.dp)
                            )
                        },
                        state = datePickerState
                    )
                }
            )
        }

        if (createCampForm.showEndDatePicker){
            DatePickerDialog(
                onDismissRequest = {
                    createCampViewModel.onCreateCampAction(
                        CreateCampAction.IsEndDatePickerVisible(false)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            createCampViewModel.onCreateCampAction(
                                CreateCampAction.OnEndDateChange(datePickerState.selectedDateMillis?.let { Instant.fromEpochMilliseconds(it) }!!)
                            )
                        },
                        content = {
                            Text(
                                text = "Confirm"
                            )
                        }
                    )
                },
                content = {
                    DatePicker(
                        title = {
                            Text(
                                text = "Select end date",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(12.dp)
                            )
                        },
                        state = datePickerState
                    )
                }
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {

            item {
                AppTextField(
                    label = "Camp Name",
                    value = createCampForm.name,
                    placeholder = "Enter Camp name",
                    onValueChanged = { name ->
                        createCampViewModel.onCreateCampAction(
                            CreateCampAction.OnNameChange(name)
                        )
                    },
                    error = createCampForm.nameError
                )
            }

            item {
                AppTextField(
                    label = "Curriculum",
                    value = createCampForm.curriculum?.text ?: "",
                    placeholder = "Enter curriculum",
                    onValueChanged = {},
                    error = createCampForm.curriculumError,
                    enabled = false,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                createCampViewModel.onCreateCampAction(
                                    CreateCampAction.IsCurriculumPickerVisible(isVisible = !createCampForm.showCurriculumList)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Show curriculum list"
                            )
                        }
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                createCampViewModel.onCreateCampAction(
                                    CreateCampAction.IsCurriculumPickerVisible(isVisible = !createCampForm.showCurriculumList)
                                )
                            }
                        )
                )
            }

            item {
                AppTextField(
                    label = "School",
                    value = createCampForm.school?.name ?: "",
                    placeholder = "Enter school",
                    onValueChanged = {},
                    error = createCampForm.schoolError,
                    enabled = false,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                createCampViewModel.onCreateCampAction(
                                    CreateCampAction.IsSchoolPickerVisible(isVisible = !createCampForm.showSchoolList)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Show school list"
                            )
                        }
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                createCampViewModel.onCreateCampAction(
                                    CreateCampAction.IsSchoolPickerVisible(isVisible = !createCampForm.showSchoolList)
                                )
                            }
                        )
                )
            }

            item {
                AppTextField(
                    label = "Organization",
                    value = createCampForm.organization?.name ?: "",
                    placeholder = "Enter organization",
                    onValueChanged = {},
                    error = createCampForm.organizationError,
                    enabled = false,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                createCampViewModel.onCreateCampAction(
                                    CreateCampAction.IsOrganizationPickerVisible(isVisible = !createCampForm.showOrganizationList)
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
                                createCampViewModel.onCreateCampAction(
                                    CreateCampAction.IsOrganizationPickerVisible(isVisible = !createCampForm.showOrganizationList)
                                )
                            }
                        )
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AppTextField(
                        enabled = false,
                        label = "Start",
                        value = formatDateTimeToDateMonth(createCampForm.startDate ?: Clock.System.now()),
                        onValueChanged = {},
                        onTextFieldClick = {
                            createCampViewModel.onCreateCampAction(
                                CreateCampAction.IsStartDatePickerVisible(true)
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        placeholder = "start date",
                        error = createCampForm.startDateError,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    createCampViewModel.onCreateCampAction(
                                        CreateCampAction.IsStartDatePickerVisible(true)
                                    )
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(R.drawable.calendar),
                                        contentDescription = "show calendar",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            )
                        },
                        modifier = Modifier
                            .width(150.dp)
                    )

                    AppTextField(
                        enabled = false,
                        label = "End",
                        value = formatDateTimeToDateMonth(createCampForm.endDate ?: Clock.System.now()),
                        onValueChanged = {},
                        onTextFieldClick = {
                            createCampViewModel.onCreateCampAction(
                                CreateCampAction.IsEndDatePickerVisible(true)
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        placeholder = "end date",
                        error = createCampForm.endDateError,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    createCampViewModel.onCreateCampAction(
                                        CreateCampAction.IsEndDatePickerVisible(true)
                                    )
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(R.drawable.calendar),
                                        contentDescription = "show calendar",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            )
                        },
                        modifier = Modifier
                            .width(150.dp)
                    )

                }
            }

            item {
                AppButton(
                    onClick = {
                        createCampViewModel.onCreateCampAction(CreateCampAction.SubmitCamp)
                    }
                ) {
                    when(createCampResponse.status){
                        ResultStatus.LOADING -> {
                            AppButtonLoadingContent()
                        }
                        ResultStatus.INITIAL ,
                        ResultStatus.SUCCESS ,
                        ResultStatus.ERROR -> {
                            Text(
                                "Submit"
                            )
                        }
                    }
                }
            }

        }
    }
}