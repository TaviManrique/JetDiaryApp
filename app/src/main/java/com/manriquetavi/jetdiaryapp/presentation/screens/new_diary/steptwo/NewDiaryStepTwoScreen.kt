package com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.steptwo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewDiaryStepTwoScreen(
    uiEvent: NewDiaryStepTwoUiEvent,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    onBackPressed: () -> Unit,
    onSavedClicked: () -> Unit
) {
    Scaffold(
        topBar = { NewDiaryStepTwoTopBar(onBackPressed = onBackPressed) },
    ) { paddingValues ->
        when (uiEvent) {
            NewDiaryStepTwoUiEvent.CreateNewDiary -> NewDiaryStepTwoContent(
                description = description,
                onDescriptionChanged = onDescriptionChanged,
                onSavedClicked = onSavedClicked,
                paddingValues = paddingValues
            )
            NewDiaryStepTwoUiEvent.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun NewDiaryStepTwoScreenPreview() {
    NewDiaryStepTwoScreen(
        uiEvent = NewDiaryStepTwoUiEvent.CreateNewDiary,
        description = "",
        onDescriptionChanged = {},
        onBackPressed = {},
        onSavedClicked = {}
    )
}