package com.example.graphqlandroid.presentation.school

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.graphqlandroid.domain.models.school.AppCamp
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.presentation.common.RowTitleAndText
import com.example.graphqlandroid.utils.Utils

@Composable
fun CampItem(
    modifier: Modifier = Modifier,
    camp: AppCamp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .sizeIn(minWidth = 100.dp, minHeight = 120.dp, maxWidth = 240.dp, maxHeight = 200.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Text(
                text = camp.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.padding(8.dp))

            RowTitleAndText(
                title = "Curriculum",
                text = camp.curriculum,
                modifier = Modifier
                    .fillMaxWidth()
            )

            RowTitleAndText(
                title = "Start",
                text = Utils.formatDate(inputDate = camp.startDate),
                modifier = Modifier
                    .fillMaxWidth()

            )

            RowTitleAndText(
                title = "End",
                text = Utils.formatDate(inputDate = camp.endDate),
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
    }

}


@Preview
@Composable
fun PreviewCampItem(
    camp: AppCamp = AppCamp(
        id = "1",
        name = "The Camp",
        startDate = "2024-03-01",
        endDate = "2024-03-01",
        curriculum = "Singing",
        schoolId = "2"
    )
){
    CampItem(camp = camp)
}