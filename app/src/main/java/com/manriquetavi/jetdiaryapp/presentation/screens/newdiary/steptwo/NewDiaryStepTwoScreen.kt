package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewDiaryStepTwoScreen(
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = { NewDiaryStepTwoTopBar(onBackPressed = onBackPressed) },
    ) { paddingValues ->
        NewDiaryStepTwoContent(
            paddingValues
        )
    }
}

@Preview
@Composable
fun NewDiaryStepTwoScreenPreview() {
    NewDiaryStepTwoScreen(
        onBackPressed = {}
    )
}