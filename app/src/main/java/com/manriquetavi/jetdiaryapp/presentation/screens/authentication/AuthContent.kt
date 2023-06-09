package com.manriquetavi.jetdiaryapp.presentation.screens.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.presentation.components.GoogleButton

@Composable
fun AuthContent(
    loadingState: Boolean,
    onSignInClicked: () -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
    ) {
        GoogleButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            loadingState = loadingState,
            onClick = onSignInClicked
        )
    }
}