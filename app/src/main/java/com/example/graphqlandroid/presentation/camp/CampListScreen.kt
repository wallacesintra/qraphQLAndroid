package com.example.graphqlandroid.presentation.camp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.domain.models.ResultStatus
import com.example.graphqlandroid.domain.viewmodels.camp.CampViewModel
import com.example.graphqlandroid.navController
import com.example.graphqlandroid.presentation.common.AppCircularLoading
import com.example.graphqlandroid.presentation.navigation.CampPage
import com.example.graphqlandroid.presentation.school.CampItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampListScreen(
    modifier: Modifier = Modifier
) {
    val campViewModel = koinViewModel<CampViewModel>()
    val campListState by campViewModel.campStateFlow.collectAsState()

    AnimatedContent(
        targetState = campListState.status,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {targetState ->
        when(targetState){
            ResultStatus.INITIAL,
            ResultStatus.LOADING -> {
                AppCircularLoading()
            }
            ResultStatus.SUCCESS -> {
                campListState.data?.let {camps ->

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(200.dp),
                        verticalItemSpacing = 8.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        items(camps, {it.id}){ camp ->
                            CampItem(
                                camp = camp,
                                onClick = {
                                    navController.navigate(CampPage(id = camp.id))
                                }
                            )
                        }
                    }
                }
            }
            ResultStatus.ERROR -> {}
        }
    }

}