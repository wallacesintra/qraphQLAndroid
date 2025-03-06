package com.example.graphqlandroid.presentation.students.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.graphqlandroid.R
import com.example.graphqlandroid.domain.models.students.AppStudent
import com.example.graphqlandroid.presentation.common.ColumnTitleAndText
import com.example.graphqlandroid.presentation.common.ImageBox

@Composable
fun DashboardStudentItem(
    modifier: Modifier = Modifier,
    student: AppStudent
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {

        val imageList = listOf(
            R.drawable.female_black1,
            R.drawable.female_black2,
            R.drawable.male_black1
        )

        val profileImage = remember {  imageList.random() }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,

            modifier = modifier
        ) {
            ImageBox(
                painterResId = profileImage,
                shape = RoundedCornerShape(20),
                isImage = true,
                imageSize = 40.dp
            )

            ColumnTitleAndText(
                title = "${student.firstName} ${student.lastName}",
                text = "Age: ${student.age}",
            )
        }



        Text(
            text = "Grade ${student.grade}",
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewStudentItem(student: AppStudent = AppStudent(
    age = 10,
    campId = "1",
    firstName = "John",
    grade = 5,
    id = "1",
    lastName = "Doe",
    camp = com.example.graphqlandroid.domain.models.school.AppCamp(
        id = "1",
        name = "Camp",
        startDate = "2022-01-01",
        endDate = "2022-01-01",
        curriculum = "Curriculum",
        schoolId = "1"
    )
)) {
    DashboardStudentItem(student= student)
}