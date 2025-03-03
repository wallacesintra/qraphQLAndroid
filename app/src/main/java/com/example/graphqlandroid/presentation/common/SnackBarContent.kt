package com.example.graphqlandroid.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.domain.models.SnackBarItem

@Composable
fun SnackBarContent(
    modifier: Modifier = Modifier,
    description: String = "",
    snackBarHostState: SnackbarHostState,
    snackBarItem: SnackBarItem?,
    alignBottom: Boolean = false,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(top = 12.dp).testTag(description),
        contentAlignment = if (alignBottom) Alignment.BottomCenter else Alignment.TopCenter,
    ) {
        SnackbarHost(
            hostState = snackBarHostState,
            snackbar = {
                Card(
                    shape = MaterialTheme.shapes.small,
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                snackBarHostState.currentSnackbarData?.dismiss()
                            },
                    border =
                        BorderStroke(
                            1.dp,
                            if (snackBarItem?.isError == true) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = 0.5f,
                                )
                            },
                        ),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = if(snackBarItem?.isError == true) MaterialTheme.colorScheme.onErrorContainer else Color.Green.copy(alpha = 0.4f),
                        ),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier =
                                Modifier
                                    .fillMaxWidth(.85f),
                            text = it.visuals.message,
                            style =
                                MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = if(snackBarItem?.isError == true) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary,
                                ),
                        )

                        IconButton(onClick = {
                            snackBarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Icon(
                                imageVector = if (snackBarItem?.isError == true) Icons.Default.Clear else Icons.Default.CheckCircle,
                                tint = if (snackBarItem?.isError == true) MaterialTheme.colorScheme.error else Color.Green,
                                contentDescription = null,
                            )
                        }
                    }
                }
            },
        )
    }
}
