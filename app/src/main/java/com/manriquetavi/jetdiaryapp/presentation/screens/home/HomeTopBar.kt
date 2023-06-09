package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.presentation.components.ThemeSwitcher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    darkTheme: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked:() -> Unit,
    onCalendarClicked:() -> Unit,
    onThemeUpdate: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick =  onMenuClicked) {
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
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold
                ) ,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            ThemeSwitcher(
                darkTheme = darkTheme,
                onClick = onThemeUpdate
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    )
}