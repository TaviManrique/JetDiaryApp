package com.manriquetavi.jetdiaryapp.presentation.screens.update_diary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.manriquetavi.jetdiaryapp.presentation.screens.home.EmptyContent

@Composable
internal fun UpdateDiaryScreen(
    uiState: UiState,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            UpdateDiaryTopBar(
                onBackPressed = onBackPressed
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is UiState.Success -> {
                UpdateDiaryContent(
                    onDescriptionChanged = {},
                    diary = uiState.diary,
                    paddingValues = paddingValues
                )
            }
            is UiState.Error -> {
                EmptyContent(
                    title = "Error",
                    subtitle = uiState.message
                )
            }
            is UiState.Loading -> {
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