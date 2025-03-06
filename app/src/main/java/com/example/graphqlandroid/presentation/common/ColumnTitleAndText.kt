package com.example.graphqlandroid.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ColumnTitleAndText(
    modifier: Modifier = Modifier,
    title: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    titleFontWeight: FontWeight = FontWeight.Normal,
    text: String,
    textTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
){
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = titleTextStyle,
            fontWeight = titleFontWeight
        )

        Text(
            text = text,
            style = textTextStyle,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}