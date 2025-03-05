package com.example.graphqlandroid.presentation.camp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.camp.IndividualCampViewModel
import com.example.graphqlandroid.navController
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.common.ImageBox
import com.example.graphqlandroid.presentation.common.RowTitleAndText
import com.example.graphqlandroid.presentation.dashboard.components.DashboardItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CampScreen(
    modifier: Modifier = Modifier,
    campId: String
) {

    val individualCampViewModel = koinViewModel<IndividualCampViewModel>()
    val campState by individualCampViewModel.campStateFlow.collectAsState()

    LaunchedEffect(campId) {
        individualCampViewModel.refresh(campId = campId)
    }

    Scaffold(
        topBar = {
            IconButton(
                onClick = { navController.popBackStack()}
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {innerPadding ->

        AnimatedContent(
            targetState = campState.status,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { targetState ->
            when(targetState){
                ResultStatus.INITIAL,
                ResultStatus.LOADING -> {
                    AppCircularLoading()
                }
                ResultStatus.SUCCESS -> {
                    campState.data?.let {camp ->

                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                        ) {
                            item{
                                ImageBox(
                                    painterResId = R.drawable.camping_24dp_e8eaed_fill0_wght400_grad0_opsz24,
                                    imageSize = 80.dp,
                                    iconSize = 40.dp
                                )

                                Text(
                                    text = camp.name,
                                    style = MaterialTheme.typography.titleMedium
                                )


                                FlowRow(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    TextButton(
                                        onClick = {},
                                        enabled = false,
                                        colors = ButtonDefaults.textButtonColors(
                                            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    ) {
                                        Text(
                                            camp.school?.name ?: ""
                                        )
                                    }

                                    camp.organization?.let {
                                        TextButton(
                                            onClick = {},
                                            enabled = false,
                                            colors = ButtonDefaults.textButtonColors(
                                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        ) {
                                            Text(
                                                camp.organization.name
                                            )
                                        }

                                    }

                                }

                            }

                            /*
                            item {
                                FlowRow(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    DashboardItem(
                                        text = "Students",
                                        count = camp.studentsSize,
                                        icon = R.drawable.person
                                    )
                                    DashboardItem(
                                        text = "Groups",
                                        count = camp.campGroupsSize,
                                        icon = R.drawable.group_24dp_e8eaed_fill0_wght400_grad0_opsz24
                                    )
                                }
                            }*/

                            item {

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .widthIn(max = 900.dp)
                                ) {
                                    RowTitleAndText(
                                        title = "Curriculum",
                                        text = camp.curriculum
                                    )
                                    RowTitleAndText(
                                        title = "Students",
                                        text = camp.studentsSize.toString()
                                    )
                                    RowTitleAndText(
                                        title = "Groups",
                                        text = camp.campGroupsSize.toString()
                                    )

                                }


                            }
                        }
                    }
                }
                ResultStatus.ERROR -> {}
            }

        }
    }

}