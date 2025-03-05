package com.example.graphqlandroid.presentation.school

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.schools.IndividualSchoolViewModel
import com.example.graphqlandroid.navController
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.common.AppOpenGoogleMap
import com.example.graphqlandroid.presentation.common.ImageBox
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SchoolScreen(
    modifier: Modifier = Modifier,
    schoolId: String
) {

    val individualSchoolViewModel = koinViewModel<IndividualSchoolViewModel>()
    val schoolState by individualSchoolViewModel.schoolStateFlow.collectAsState()

    LaunchedEffect(schoolId) {
        individualSchoolViewModel.refresh(school_id = schoolId)
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
            targetState = schoolState.status,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) { targetState ->
            when(targetState){
                ResultStatus.INITIAL,
                ResultStatus.LOADING -> {
                    AppCircularLoading()
                }
                ResultStatus.SUCCESS -> {
                    schoolState.data?.let { school ->

                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            item {
                                ImageBox(
                                    painterResId = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz24_1_,
                                    imageSize = 80.dp,
                                    iconSize = 40.dp
                                )

                                Text(
                                    text = school.name,
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
                                            school.county?.name ?: ""
                                        )
                                    }

                                    school.county?.country?.let {
                                        TextButton(
                                            onClick = {},
                                            enabled = false,
                                            colors = ButtonDefaults.textButtonColors(
                                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        ) {
                                            Text(
                                                school.county.country.name
                                            )
                                        }

                                    }

                                }

                            }

                            item {
                                Text(
                                    text = "Camps"
                                )
                                Spacer(Modifier.padding(4.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                ) {
                                    if (school.camps.isEmpty()){
                                        item {
                                            Text("No camps available")
                                        }
                                    }else{
                                        items(school.camps, {it.id}){ camp ->
                                            CampItem(camp = camp)
                                        }
                                    }

                                }
                            }

                            item {
                                AppOpenGoogleMap(
                                    latitude = school.county?.latitude?.toDouble() ?: 0.0,
                                    longitude = school.county?.longitude?.toDouble() ?: 0.0
                                )
                            }

                        }
                    }

                }
                ResultStatus.ERROR -> {}
            }
        }

    }

}