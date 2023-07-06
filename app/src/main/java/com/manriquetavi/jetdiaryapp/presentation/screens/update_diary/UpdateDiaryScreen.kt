package com.manriquetavi.jetdiaryapp.presentation.screens.update_diary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.presentation.screens.home.EmptyContent
import com.manriquetavi.jetdiaryapp.util.RequestState

@Composable
fun UpdateDiaryScreen(
    onBackPressed: () -> Unit,
    diaryState: RequestState<Diary>
) {
    Scaffold(
        topBar = {
            UpdateDiaryTopBar(
                onBackPressed = onBackPressed
            )
        }
    ) { paddingValues ->
        when (diaryState) {
            is RequestState.Success -> {
                UpdateDiaryContent(
                    onDescriptionChanged = {},
                    diary = diaryState.data,
                    paddingValues = paddingValues
                )
            }
            is RequestState.Error -> {
                EmptyContent(
                    title = "Error",
                    subtitle = "${diaryState.error.message}"
                )
            }
            is RequestState.Idle -> { }
            is RequestState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}