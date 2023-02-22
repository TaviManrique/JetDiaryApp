package com.manriquetavi.jetdiaryapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manriquetavi.jetdiaryapp.presentation.components.CommonAlertDialog
import com.manriquetavi.jetdiaryapp.presentation.screens.authentication.AuthScreen
import com.manriquetavi.jetdiaryapp.presentation.screens.authentication.AuthViewModel
import com.manriquetavi.jetdiaryapp.presentation.screens.home.HomeScreen
import com.manriquetavi.jetdiaryapp.util.Constants.APP_ID
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.math.sign

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            }
        )
        writeRoute()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val authViewModel: AuthViewModel = viewModel()
        val authenticated = authViewModel.authenticated
        val loadingState = authViewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        AuthScreen(
            loadingState = loadingState.value,
            authenticated = authenticated.value,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onSignInClicked = {
                oneTapState.open()
                authViewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                authViewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        authViewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        authViewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = {
                messageBarState.addError(Exception(it))
                authViewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var dialogOpened by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        HomeScreen(
            drawerState = drawerState,
            signOutClicked = {
                dialogOpened = true
            },
            onProfileClicked = {
                scope.launch { drawerState.open() }
            },
            onCalendarClicked = {},
            navigateToWrite = navigateToWrite
        )
        CommonAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want sign out?",
            dialogOpened = dialogOpened,
            onCloseDialog = { dialogOpened  = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    if(user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth
                        }
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(
            navArgument(name = "diaryId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {

    }
}
