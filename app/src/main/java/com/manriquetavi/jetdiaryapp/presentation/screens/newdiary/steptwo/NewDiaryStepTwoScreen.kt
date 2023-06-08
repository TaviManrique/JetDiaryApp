package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.util.RequestState

@Composable
fun NewDiaryStepTwoScreen(
    resultAddDiary: RequestState<Diary>,
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    onBackPressed: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onSavedClicked: () -> Unit
) {
    Scaffold(
        topBar = { NewDiaryStepTwoTopBar(onBackPressed = onBackPressed) },
    ) { paddingValues ->
        NewDiaryStepTwoContent(
            resultAddDiary = resultAddDiary,
            title = title,
            onTitleChanged = onTitleChanged,
            description = description,
            onDescriptionChanged = onDescriptionChanged,
            navigateToHomeScreen = navigateToHomeScreen,
            onSavedClicked = onSavedClicked,
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
        onBackPressed = {},
        resultAddDiary = RequestState.Idle,
        navigateToHomeScreen = {},
        onSavedClicked = {}
    )
}