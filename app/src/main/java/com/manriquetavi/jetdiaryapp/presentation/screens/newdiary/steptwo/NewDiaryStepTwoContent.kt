package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.presentation.components.ButtonAnimation

@Composable
fun NewDiaryStepTwoContent(
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth()
                .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ) {
            ButtonAnimation(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(320.dp),
                onClick = {}
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
}

@Preview(showBackground = true)
@Composable
fun NewDiaryStepTwoContentPreview() {
    NewDiaryStepTwoContent(
        paddingValues = PaddingValues()
    )
}