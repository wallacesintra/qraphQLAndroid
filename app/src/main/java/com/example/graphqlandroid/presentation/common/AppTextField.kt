package com.example.graphqlandroid.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String,
    placeholder: String,
    error: String?,
    singleLine: Boolean = true,
    onValueChanged: (String) -> Unit,
    onTextFieldClick: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    passwordVisible: Boolean = true,
    enabled: Boolean = true,
    required: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable () -> Unit = {},
    leadingIcon: @Composable() (() -> Unit)? = null,
    textFieldModifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        if (label.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(vertical = 4.dp),
                )

                if (required) {
                    Text(
                        text = " *",
                        style =
                            MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.error,
                            ),
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }
            }
        }

        TextField(
            enabled = enabled,
            value = value,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray),
                )
            },
            maxLines = 1,
            colors =
                TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                ),
            leadingIcon = leadingIcon,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = trailingIcon,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .clickable(
                    onClick = onTextFieldClick
                )
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag(label.lowercase()),
        )
        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
