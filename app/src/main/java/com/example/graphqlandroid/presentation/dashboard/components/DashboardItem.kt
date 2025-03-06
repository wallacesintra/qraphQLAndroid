package com.example.graphqlandroid.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.utils.Utils

@Composable
fun DashboardItem(
    modifier: Modifier = Modifier,
    text: String,
    count: Int,
    icon: Int,
) {

    val colorList = listOf(
        MaterialTheme.colorScheme.inversePrimary,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.primaryContainer
    )

    val backgroundColor = remember { colorList.random() }

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .widthIn(min = 110.dp, max = 700.dp)
            .heightIn(min = 120.dp, max = 160.dp )
            .clip(RoundedCornerShape(5))
            .border(
                width = 1.dp,
//                color = LocalContentColor.current.copy(alpha = 0.1f),
                color = LocalContentColor.current.copy(alpha = 0.1f),
                shape = RoundedCornerShape(5)
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text= Utils.animatedNumberString(number = count.toString(), animatedPlayed = animationPlayed),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = LocalContentColor.current,
                    fontStyle = FontStyle.Italic
                )
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = LocalContentColor.current.copy(alpha = 0.7f)
            )
        }

        Icon(
            painter = painterResource(icon),
            contentDescription = text,
            tint = LocalContentColor.current.copy(alpha = 0.3f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        )
    }

}