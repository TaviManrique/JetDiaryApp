package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.R
import com.manriquetavi.jetdiaryapp.domain.repository.Diaries
import com.manriquetavi.jetdiaryapp.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    darkTheme: Boolean,
    diaries: RequestState<Diaries>,
    drawerState: DrawerState,
    signOutClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    navigateToNewDiary: () -> Unit,
    navigateToUpdateDiary: (String) -> Unit,
    onThemeUpdate: () -> Unit
) {
    var padding by remember { mutableStateOf(PaddingValues()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    NavigationDrawer(
        drawerState = drawerState,
        signOutClicked = signOutClicked
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopBar(
                    darkTheme = darkTheme,
                    scrollBehavior = scrollBehavior,
                    onMenuClicked = onProfileClicked,
                    onCalendarClicked = onCalendarClicked,
                    onThemeUpdate = onThemeUpdate
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(end = padding.calculateEndPadding(LayoutDirection.Ltr)),
                    onClick = navigateToNewDiary
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "New Diary Icon"
                    )
                }
            }
        ) { paddingValues ->
            padding = paddingValues
            when (diaries) {
                is RequestState.Success -> {
                    HomeContent(
                        diariesWithDates = diaries.data,
                        onClickDiary = navigateToUpdateDiary,
                        paddingValues = paddingValues
                    )
                }
                is RequestState.Error -> {
                    EmptyContent(
                        title = "Error",
                        subtitle = "${diaries.error.message}"
                    )
                }
                RequestState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    signOutClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(250.dp),
                        painter = painterResource(id = R.drawable.new_logo),
                        contentDescription = "Logo Item"
                    )
                }
                NavigationDrawerItem(
                    label = {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = "Google Logo"
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Sign Out",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    selected = false,
                    onClick = signOutClicked
                )
            }
        }
    ) {
        content()
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        darkTheme = false,
        diaries = RequestState.Loading,
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        onProfileClicked = { },
        signOutClicked = { },
        onCalendarClicked = { },
        navigateToNewDiary = { },
        navigateToUpdateDiary = { },
        onThemeUpdate = { }
    )
}