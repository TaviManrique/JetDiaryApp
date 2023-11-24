package com.manriquetavi.jetdiaryapp.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CommonAlertDialog(
    title: String,
    message: String,
    dialogOpened: Boolean,
    onCloseDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (dialogOpened) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        onCloseDialog()
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onCloseDialog
                ) {
                    Text(text = "No")
                }
            },
            onDismissRequest = onCloseDialog
        )
    }
}

@Preview
@Composable
fun CommonAlertDialogPreview() {
    CommonAlertDialog(
        title = "Title",
        message = "message",
        dialogOpened = true,
        onCloseDialog = {},
        onYesClicked = {}
    )
}