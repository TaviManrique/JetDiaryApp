package com.manriquetavi.jetdiaryapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.domain.model.Mood

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