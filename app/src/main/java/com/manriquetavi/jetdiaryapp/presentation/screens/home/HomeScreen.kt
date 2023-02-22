package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manriquetavi.jetdiaryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    signOutClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    navigateToWrite: () -> Unit
) {
    /*
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xffffe7e4),
                        Color(0xfffe868f)
                    )
                )
            )
    ) {

    }*/
    
    NavigationDrawer(
        drawerState = drawerState,
        signOutClicked = signOutClicked
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onProfileClicked = onProfileClicked,
                    onCalendarClicked = onCalendarClicked
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = navigateToWrite) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "New Diary Icon"
                    )
                }
            }
        ) { paddingValues ->
            HomeContent(
                paddingValues = paddingValues
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                        painter = painterResource(id = R.drawable.logo),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        onProfileClicked = { },
        signOutClicked = { },
        onCalendarClicked = { },
        navigateToWrite = { }
    )
}