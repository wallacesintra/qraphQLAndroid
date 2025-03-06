package com.example.graphqlandroid.presentation.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.HomeViewModel
import com.example.graphqlandroid.domain.viewmodels.dashboard.DashboardViewModel
import com.example.graphqlandroid.presentation.camp.components.DashboardCampItem
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.dashboard.components.DashBoardContent
import com.example.graphqlandroid.presentation.dashboard.components.DashboardItem
import com.example.graphqlandroid.presentation.home.AppScreen
import com.example.graphqlandroid.presentation.students.components.DashboardStudentItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {

    val dashboardViewModel = koinViewModel<DashboardViewModel>()
    val countState by dashboardViewModel.countStateFlow.collectAsState()
    val userState by dashboardViewModel.userStateFlow.collectAsState()
    val studentsList by dashboardViewModel.studentsList.collectAsState()
    val campsListState by dashboardViewModel.campsListStateFlow.collectAsState()


    val homeViewModel = koinViewModel<HomeViewModel>()

    AnimatedContent(
        targetState = countState.status,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)

    ) { targetState ->
        when(targetState){
            ResultStatus.INITIAL ,
            ResultStatus.LOADING -> { AppCircularLoading() }
            ResultStatus.SUCCESS -> {

                LazyColumn(
//                    columns = GridCells.Adaptive(460.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ) {


                    if (countState.data == null) return@LazyColumn

                    item {
                        Spacer(Modifier.padding(4.dp))

                        Text(
                            text = "Hello, ${userState.data?.firstName} \uD83D\uDC4B",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(Modifier.padding(8.dp))

                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            DashboardItem(
                                count = countState.data!!.schoolsCount,
                                text = "Schools",
                                icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz24_1_
                            )

                            DashboardItem(
                                count = countState.data!!.studentsCount,
                                text = "Students",
                                icon = R.drawable.group_24dp_e8eaed_fill0_wght400_grad0_opsz24
                            )

                            DashboardItem(
                                count = countState.data!!.campsCount,
                                text = "Camps",
                                icon = R.drawable.camping_24dp_e8eaed_fill0_wght400_grad0_opsz24
                            )
                        }

                    }

                    item {
                        DashBoardContent(
                            title = "Students",
                            onShowMoreClick = {homeViewModel.updateScreen(AppScreen.Students)}
                        ) {
                            studentsList.data?.let { students ->
                                students.forEachIndexed { index, appStudent ->
                                    key(index) {
                                        DashboardStudentItem(
                                            student = appStudent
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        DashBoardContent(
                            title = "Camps",
                            onShowMoreClick = { homeViewModel.updateScreen(AppScreen.Camp) }
                        ) {
                            campsListState.data?.let { camps ->
                                camps.forEachIndexed { index, appCamp ->
                                    key(index) {
                                        DashboardCampItem(appCamp = appCamp)
                                    }
                                }
                            }
                        }

                    }



                    item { Spacer(Modifier.padding(24.dp)) }

                }


            }
            ResultStatus.ERROR -> {}
        }
    }

}