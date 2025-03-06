package com.example.graphqlandroid.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.presentation.home.AppScreen
import com.example.graphqlandroid.presentation.students.components.DashboardStudentItem

@Composable
fun DashBoardContent(
    modifier: Modifier = Modifier,
    title: String = "",
    onShowMoreClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5))
            .sizeIn(minHeight = 300.dp, maxHeight = 400.dp, minWidth = 300.dp, maxWidth = 500.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(8.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.padding(4.dp))

            content.invoke()

        }

        Text(
            text = "see more",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clickable(
                    onClick = onShowMoreClick
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        )
    }

}