package com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.steptwo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.presentation.components.ButtonAnimation

@Composable
fun NewDiaryStepTwoContent(
    description: String,
    onDescriptionChanged: (String) -> Unit,
    onSavedClicked: () -> Unit,
    paddingValues: PaddingValues,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .padding(bottom = paddingValues.calculateBottomPadding())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                value = description,
                onValueChange = onDescriptionChanged,
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(alpha = 0.5f),
                        text = "Start writing a note ..."
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ButtonAnimation(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                onClick = { onSavedClicked() }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Save",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewDiaryStepTwoContentPreview() {
    NewDiaryStepTwoContent(
        description = "",
        onDescriptionChanged = {},
        paddingValues = PaddingValues(),
        onSavedClicked = {  }
    )
}