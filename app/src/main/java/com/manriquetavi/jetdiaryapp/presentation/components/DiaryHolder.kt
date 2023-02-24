package com.manriquetavi.jetdiaryapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.model.Mood
import com.manriquetavi.jetdiaryapp.ui.theme.Elevation
import com.manriquetavi.jetdiaryapp.util.toInstant
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Composable
fun DateHeader(localDate: LocalDate) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("%02d", localDate.dayOfMonth),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Light
                    )
                )
                Text(
                    text = localDate.dayOfWeek.toString().take(3),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
        Text(
            text = localDate.month.toString().lowercase().replaceFirstChar { it.titlecase() },
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Normal
            )
        )
        Text(
            text = "${localDate.year}",
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun DiaryHolder(diary: Diary, onClickDiary: (String) -> Unit) {
    val localDensity = LocalDensity.current
    var componentHeight by remember { mutableStateOf(0.dp) }
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClickDiary(diary._id.toString()) }
    ) {
        Spacer(modifier = Modifier.width(14.dp))
        Surface(
            modifier = Modifier
                .width(2.dp)
                .height(componentHeight + 14.dp),
            tonalElevation = Elevation.Level1
        ) {}
        Spacer(modifier = Modifier.width(20.dp))
        Surface(
            modifier = Modifier
                .clip(shape = Shapes().medium)
                .onGloballyPositioned {
                    componentHeight = with(localDensity) { it.size.height.toDp() }
                },
            tonalElevation = Elevation.Level1,
            color = Mood.valueOf(diary.mood).contentColor.copy(0.37f)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = SimpleDateFormat("hh:mm a", Locale.US).format(Date.from(diary.date.toInstant())),
                    color = Mood.valueOf(diary.mood).contentColor
                )
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = Mood.valueOf(diary.mood).icon),
                    contentDescription = "Mood Icon"
                )
                Text(
                    modifier = Modifier.padding(14.dp),
                    color = Mood.valueOf(diary.mood).contentColor,
                    text = diary.description,
                    style = TextStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun DateHeaderPreview() {
    Row(modifier = Modifier.fillMaxWidth()) {
        DateHeader(localDate = LocalDate.now())
    }
}

@Composable
@Preview(showBackground = true)
fun DiaryHolderPreview() {
    DiaryHolder(
        diary = Diary().apply {
            title = "My diary"
            description = "lorem asdkjfhakds adjsgfhadkgjha asdfgjkahfgkjahfgkaf aksdghaksdghaisdofguhaiughadfiguhad"
            mood = Mood.Crying.name
        },
        onClickDiary = {}
    )
}