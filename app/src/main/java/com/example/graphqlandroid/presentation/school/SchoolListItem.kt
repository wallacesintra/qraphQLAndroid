package com.example.graphqlandroid.presentation.school

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.models.school.AppSchool

@Composable
fun SchoolListItem(
    modifier: Modifier = Modifier,
    school: AppSchool,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .widthIn(max = 600.dp, min = 360.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                onClick = onClick
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(
                    painter = painterResource(R.drawable.school_item_icon),
                    contentDescription = "school icon",
                    modifier = Modifier
                        .size(32.dp)
                )
            }


            Column {
                Text(
                    text = school.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${school.countyName}, ${school.countryName}",
                    style = MaterialTheme.typography.bodySmall.copy(

                    )
                )
            }
        }

        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewSchoolListItem(){
    SchoolListItem(
        school = AppSchool(id = "1", name = "Sinatra DetailedSchool", countyName = "Nairobi", countryName = "Kenya")
    )
}