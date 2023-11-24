package com.manriquetavi.jetdiaryapp.presentation.screens.update_diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.model.moods
import com.manriquetavi.jetdiaryapp.presentation.components.SelectableMoodItem

@Composable
fun UpdateDiaryContent(
    onDescriptionChanged: (String) -> Unit,
    diary: Diary,
    paddingValues: PaddingValues
) {
    var moodIndexSelected by remember { mutableIntStateOf(moods.indexOfFirst { it.name == diary.mood }) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(moods) { index, mood ->
                SelectableMoodItem(
                    mood = mood,
                    selected = moodIndexSelected == index
                ) {
                    moodIndexSelected = if (moodIndexSelected != index) index else moodIndexSelected
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            value = diary.description,
            onValueChange = onDescriptionChanged,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(alpha = 0.5f),
                    text = "Start writing a note ..."
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified,
                disabledIndicatorColor = Color.Unspecified,
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
}

@Preview
@Composable
fun UpdateDiaryContentPreview() {
    UpdateDiaryContent(
        onDescriptionChanged = {},
        diary = Diary(),
        paddingValues = PaddingValues()
    )
}