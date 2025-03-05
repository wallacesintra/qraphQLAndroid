package com.example.graphqlandroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.graphqlandroid.navController

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String
){

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            content = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "go back"
                )
            }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier
                .weight(1.0f)
        )


        Spacer(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.background)
        )

    }

}