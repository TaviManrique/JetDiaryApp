package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewDiaryStepTwoScreen(
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = { NewDiaryStepTwoTopBar(onBackPressed = onBackPressed) },
    ) { paddingValues ->
        NewDiaryStepTwoContent(
            title = title,
            onTitleChanged = onTitleChanged,
            description = description,
            onDescriptionChanged = onDescriptionChanged,
            paddingValues = paddingValues
        )
    }
}

@Preview
@Composable
fun NewDiaryStepTwoScreenPreview() {
    NewDiaryStepTwoScreen(
        title = "",
        onTitleChanged = {},
        description = "",
        onDescriptionChanged = {},
        onBackPressed = {}
    )
}