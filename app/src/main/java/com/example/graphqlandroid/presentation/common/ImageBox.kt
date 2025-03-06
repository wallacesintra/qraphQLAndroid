package com.example.graphqlandroid.presentation.common

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R

@Preview
@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10),
    painterResId: Int = R.drawable.female_black2,
    isImage: Boolean = false,
    backgroundColor: Color =  MaterialTheme.colorScheme.surfaceVariant,
    imageSize: Dp = 48.dp,
    iconSize: Dp = 32.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(imageSize)
            .clip(shape)
            .background(backgroundColor)
    ) {
        if (isImage){
            Image(
                painter = painterResource(painterResId),
                contentDescription = "profile image",
                contentScale = ContentScale.Crop ,
                modifier = Modifier
                    .size(imageSize)
                    .clip(shape)
            )
        }else {
            Icon(
                painter = painterResource(painterResId),
                contentDescription = "school icon",
                modifier = Modifier
                    .size(iconSize)
            )
        }
    }

}