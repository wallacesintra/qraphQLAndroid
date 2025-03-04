package com.example.graphqlandroid.presentation.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.dashboard.DashboardViewModel
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.dashboard.components.DashboardItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {

    val dashboardViewModel = koinViewModel<DashboardViewModel>()
    val countState by dashboardViewModel.countStateFlow.collectAsState()
    val userState by dashboardViewModel.userStateFlow.collectAsState()

    AnimatedContent(
        targetState = countState.status,
        modifier = modifier
            .padding(horizontal = 12.dp)

    ) { targetState ->
        when(targetState){
            ResultStatus.INITIAL ,
            ResultStatus.LOADING -> { AppCircularLoading() }
            ResultStatus.SUCCESS -> {

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Hello, ${userState.data?.firstName} \uD83D\uDC4B",
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (countState.data == null){
                        return@Column
                    }

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(100.dp),
                        verticalItemSpacing = 8.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                    ) {
                        item {
                            DashboardItem(
                                count = countState.data!!.schoolsCount,
                                text = "Schools",
                                icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz24_1_
                            )
                        }
                        item {
                            DashboardItem(
                                count = countState.data!!.studentsCount,
                                text = "Students",
                                icon = R.drawable.group_24dp_e8eaed_fill0_wght400_grad0_opsz24
                            )
                        }
                        item {
                            DashboardItem(
                                count = countState.data!!.campsCount,
                                text = "Camps",
                                icon = R.drawable.camping_24dp_e8eaed_fill0_wght400_grad0_opsz24
                            )
                        }
                    }

                }




            }
            ResultStatus.ERROR -> {}
        }
    }

}