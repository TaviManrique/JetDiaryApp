package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.presentation.components.DateHeader
import com.manriquetavi.jetdiaryapp.presentation.components.DiaryHolder
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    diariesWithDates: Map<LocalDate, List<Diary>>,
    onClickDiary: (String) -> Unit,
    paddingValues: PaddingValues
) {
    if(diariesWithDates.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            diariesWithDates.forEach { (localDate, diaries) ->
                stickyHeader(key = localDate) {
                    DateHeader(localDate = localDate)
                }
                items(
                    items = diaries,
                    key = { diary ->
                        diary._id.toString()
                    }
                ) {
                    DiaryHolder(
                        diary = it,
                        onClickDiary = onClickDiary
                    )
                }
            }
        }
    } else {
        EmptyContent(
            title = "Empty Diary",
            subtitle = "Start to write something"
        )
    }
}