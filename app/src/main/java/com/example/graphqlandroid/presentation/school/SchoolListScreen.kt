package com.example.graphqlandroid.presentation.school

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.schools.SchoolViewModel
import com.example.graphqlandroid.navController
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.navigation.SchoolPage
import org.koin.androidx.compose.koinViewModel

@Composable
fun SchoolListScreen(modifier: Modifier = Modifier) {
    val schoolViewModel = koinViewModel<SchoolViewModel>()
    val schoolsState by schoolViewModel.schoolsStateFlow.collectAsState()

    AnimatedContent(
        targetState = schoolsState.status,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) { targetState ->

        Spacer(Modifier.padding(12.dp))

        when(targetState){
            ResultStatus.INITIAL ,
            ResultStatus.LOADING -> {
                AppCircularLoading()
            }
            ResultStatus.SUCCESS -> {
                schoolsState.data?.let {schools ->

                    Column {
                        if (schools.isEmpty()) {
                            Text(
                                text = "Empty"
                            )
                            return@AnimatedContent
                        }

                        Spacer(Modifier.padding(8.dp))


                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(200.dp),
                            verticalItemSpacing = 8.dp,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                        ) {
                            items(schools, key = { it.id }) { school ->
                                SchoolListItem(
                                    school = school,
                                    onClick = { navController.navigate(SchoolPage(id = school.id)) }
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