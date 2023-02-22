package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onProfileClicked:() -> Unit,
    onCalendarClicked:() -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick =  onProfileClicked) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon"
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Home",
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = onCalendarClicked) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar Icon"
                )
            }
        }
    )
}