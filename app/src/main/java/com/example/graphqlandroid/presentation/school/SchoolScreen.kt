package com.example.graphqlandroid.presentation.school

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.schools.IndividualSchoolViewModel
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import org.koin.androidx.compose.koinViewModel

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

    AnimatedContent(
        targetState = schoolState.status,
        modifier = modifier
    ) { targetState ->
        when(targetState){
            ResultStatus.INITIAL,
            ResultStatus.LOADING -> {
                AppCircularLoading()
            }
            ResultStatus.SUCCESS -> {
                schoolState.data?.let { school ->
                    Column {
                        Text(school.name)
                        Text(school.county?.name ?: "County name")
                        Text(school.camps.size.toString())
                        Text(school.organizationId)
                    }

                }

            }
            ResultStatus.ERROR -> {}
        }
    }
}