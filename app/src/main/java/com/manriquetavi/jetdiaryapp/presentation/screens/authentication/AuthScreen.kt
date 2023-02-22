package com.manriquetavi.jetdiaryapp.presentation.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.manriquetavi.jetdiaryapp.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@ExperimentalMaterial3Api
@Composable
fun AuthScreen(
    loadingState: Boolean,
    authenticated: Boolean,
    oneTapState: OneTapSignInState,
    messageBarState: MessageBarState,
    onSignInClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { paddingValues ->
        ContentWithMessageBar(messageBarState = messageBarState) {
            AuthContent(
                loadingState = loadingState,
                onSignInClicked = onSignInClicked,
                paddingValues = paddingValues
            )
        }
    }

    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            onTokenIdReceived(tokenId)
        },
        onDialogDismissed = { message ->
            onDialogDismissed(message)
        }
    )
    
    LaunchedEffect(key1 = authenticated) {
        if (authenticated) navigateToHome()
    }
}