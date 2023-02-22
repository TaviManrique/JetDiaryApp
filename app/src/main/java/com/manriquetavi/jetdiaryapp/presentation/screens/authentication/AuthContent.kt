package com.manriquetavi.jetdiaryapp.presentation.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.R
import com.manriquetavi.jetdiaryapp.presentation.components.GoogleButton

@Composable
fun AuthContent(
    loadingState: Boolean,
    onSignInClicked: () -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(all = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Google Logo"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Welcome Back",
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
        Text(
            text = "Please sign in to continue",
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
        Spacer(modifier = Modifier.height(40.dp))
        GoogleButton(
            loadingState = loadingState,
            onClick = onSignInClicked
        )
    }
}