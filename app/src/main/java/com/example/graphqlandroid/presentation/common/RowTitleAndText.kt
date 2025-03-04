package com.example.graphqlandroid.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RowTitleAndText(
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Bold
    ),
    titleFontWeight: FontWeight = FontWeight.W700,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    title: String,
    text: String
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(100.dp)
        ) {
            Text(
                text = title,
                style = style,
//                fontWeight = titleFontWeight
            )
            Spacer(Modifier.weight(1f))
        }

        Text(
            text = text,
            style = textStyle,
//            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}