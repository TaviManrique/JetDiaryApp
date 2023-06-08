package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.stepone

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewDiaryStepOneScreen(
    onBackPressed: () -> Unit,
    navigateToNewDiaryStepTwoScreen: (Int) -> Unit
) {
    Scaffold(
        topBar = { NewDiaryStepOneTopBar(onBackPressed = onBackPressed) },
    ) { paddingValues ->
        NewDiaryStepOneContent(
            paddingValues = paddingValues,
            navigateToNewDiaryStepTwoScreen = navigateToNewDiaryStepTwoScreen
        )
    }
}

@Preview
@Composable
fun NewMoodScreenPreview() {
    NewDiaryStepOneScreen(
        onBackPressed = {}
    ) {

    }
}