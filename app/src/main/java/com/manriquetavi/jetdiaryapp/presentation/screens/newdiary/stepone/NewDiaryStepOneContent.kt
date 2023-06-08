package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.stepone

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manriquetavi.jetdiaryapp.R
import com.manriquetavi.jetdiaryapp.domain.model.Mood
import com.manriquetavi.jetdiaryapp.domain.model.moods
import com.manriquetavi.jetdiaryapp.presentation.components.ButtonAnimation
import kotlin.random.Random

@Composable
fun NewDiaryStepOneContent(
    paddingValues: PaddingValues,
    navigateToNewDiaryStepTwoScreen: (Int) -> Unit
) {
    val random = Random.nextInt(0, 14)
    var moodIndexSelected by rememberSaveable { mutableStateOf(random) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(200.dp),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(moods[moodIndexSelected].icon)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = "Mood Image"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = moods[moodIndexSelected].name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Which mood describe your feeling best?",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(5),
                contentPadding = PaddingValues(8.dp),
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
                    .width(320.dp),
                onClick = {
                    navigateToNewDiaryStepTwoScreen(moodIndexSelected)
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Continue",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

}

@Composable
fun SelectableMoodItem(
    mood: Mood,
    selected: Boolean,
    backgroundColor: Color = if (selected) mood.contentColor.copy(0.24f) else mood.contentColor.copy(0.08f),
    titleColor: Color = if (selected) mood.contentColor else mood.contentColor.copy(0.48f),
    borderColor: Color = if (selected) mood.contentColor else Color.Transparent,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .clip(shape = Shapes().small)
            .clickable {
                onClick()
            },
        tonalElevation = 2.dp,
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = borderColor
                    ),
                    shape = Shapes().small
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 8.dp),
                painter = painterResource(mood.icon),
                contentDescription = "Mood Image"
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = mood.name,
                color = titleColor,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewDiaryStepOneContentPreview() {
    NewDiaryStepOneContent(
        paddingValues = PaddingValues(),
        navigateToNewDiaryStepTwoScreen = {}
    )
}
