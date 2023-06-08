package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.presentation.components.ButtonAnimation
import com.manriquetavi.jetdiaryapp.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDiaryStepTwoContent(
    resultAddDiary: RequestState<Diary>,
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    onSavedClicked: () -> Unit,
    paddingValues: PaddingValues,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .padding(bottom = paddingValues.calculateBottomPadding())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = onTitleChanged,
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(alpha = 0.5f),
                        text = "Title"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                value = description,
                onValueChange = onDescriptionChanged,
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(alpha = 0.5f),
                        text = "Start writing a note ..."
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ButtonAnimation(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                onClick = { onSavedClicked() }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Save",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
    when(resultAddDiary) {
        is RequestState.Success -> {
            LaunchedEffect(true) {
                Toast.makeText(
                    context,
                    "Saved Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                navigateToHomeScreen()
            }
        }
        is RequestState.Error -> {
            Toast.makeText(
                context,
                "Saved Successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
        is RequestState.Loading -> Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        is RequestState.Idle -> {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewDiaryStepTwoContentPreview() {
    NewDiaryStepTwoContent(
        title = "",
        onTitleChanged = {},
        description = "",
        onDescriptionChanged = {},
        paddingValues = PaddingValues(),
        resultAddDiary = RequestState.Idle,
        navigateToHomeScreen = {},
        onSavedClicked = {  }
    )
}