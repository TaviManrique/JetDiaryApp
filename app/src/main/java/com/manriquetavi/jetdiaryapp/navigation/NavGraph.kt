package com.manriquetavi.jetdiaryapp.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.model.moods
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.presentation.components.CommonAlertDialog
import com.manriquetavi.jetdiaryapp.presentation.screens.authentication.AuthScreen
import com.manriquetavi.jetdiaryapp.presentation.screens.authentication.AuthViewModel
import com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.stepone.NewDiaryStepOneScreen
import com.manriquetavi.jetdiaryapp.presentation.screens.home.HomeScreen
import com.manriquetavi.jetdiaryapp.presentation.screens.home.HomeViewModel
import com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.steptwo.NewDiaryStepTwoScreen
import com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.steptwo.NewDiaryStepTwoViewModel
import com.manriquetavi.jetdiaryapp.presentation.screens.update_diary.UpdateDiaryScreen
import com.manriquetavi.jetdiaryapp.presentation.screens.update_diary.UpdateDiaryViewModel
import com.manriquetavi.jetdiaryapp.util.Constants.APP_ID
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@Composable
fun SetupNavGraph(
    darkTheme: Boolean,
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit,
    onThemeUpDate: () -> Unit,
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded
        )
        homeRoute(
            darkTheme = darkTheme,
            navigateToNewDiary = {
                navController.navigate(Screen.NewDiaryStepOne.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded,
            navigateToUpdateDiary = {
                navController.navigate(Screen.Update.passDiaryId(diaryId = it))
            },
            onThemeUpDate = onThemeUpDate
        )
        updateRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )
        newDiaryRoute(
            onBackPressed = {
                navController.popBackStack()
            },
            navigateToNewDiaryStepTwoScreen = {
                navController.navigate(Screen.NewDiaryStepTwo.passMoodId(it))
                Log.d("NAVIGATE", "SetupNavGraph: $it")
            },
            navigateToHomeScreen = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val authViewModel: AuthViewModel = viewModel()
        val authenticated = authViewModel.authenticated
        val loadingState = authViewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        
        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }
        
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

fun NavGraphBuilder.homeRoute(
    darkTheme: Boolean,
    navigateToNewDiary: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToUpdateDiary: (String) -> Unit,
    onDataLoaded: () -> Unit,
    onThemeUpDate: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val homeViewModel: HomeViewModel = viewModel()
        val diaries by homeViewModel.diaries.collectAsStateWithLifecycle()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var signOutDialogOpened by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = diaries) {
            onDataLoaded()
            /* Esperar a que cargue los diarios
            if (diaries !is RequestState.Loading && diaries !is RequestState.Idle) {
                onDataLoaded()
            }*/
        }

        HomeScreen(
            darkTheme = darkTheme,
            diaries = diaries,
            drawerState = drawerState,
            signOutClicked = {
                signOutDialogOpened = true
            },
            onProfileClicked = {
                scope.launch { drawerState.open() }
            },
            onCalendarClicked = {},
            navigateToNewDiary = navigateToNewDiary,
            navigateToUpdateDiary = navigateToUpdateDiary,
            onThemeUpdate = onThemeUpDate
        )

        LaunchedEffect(key1 = Unit) {
            MongoDB.configureTheRealm()
        }
        CommonAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want sign out?",
            dialogOpened = signOutDialogOpened,
            onCloseDialog = { signOutDialogOpened  = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    if(user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.updateRoute(
    onBackPressed: () -> Unit
) {
    composable(
        route = Screen.Update.route,
        arguments = listOf(
            navArgument(name = "diaryId") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val viewModel: UpdateDiaryViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        UpdateDiaryScreen(
            onBackPressed = onBackPressed,
            uiState = uiState
        )
    }
}

fun NavGraphBuilder.newDiaryRoute(
    onBackPressed: () -> Unit,
    navigateToNewDiaryStepTwoScreen: (Int) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    composable(route = Screen.NewDiaryStepOne.route) {
        NewDiaryStepOneScreen(
            onBackPressed = onBackPressed,
            navigateToNewDiaryStepTwoScreen = navigateToNewDiaryStepTwoScreen
        )
    }
    composable(
        route = Screen.NewDiaryStepTwo.route,
        arguments = listOf(
            navArgument(name = "moodId") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) {
        val context = LocalContext.current
        val newDiaryStepTwoViewModel: NewDiaryStepTwoViewModel = viewModel()
        val moodId = newDiaryStepTwoViewModel.moodId.intValue
        val description = newDiaryStepTwoViewModel.description.value
        val uiEvent = newDiaryStepTwoViewModel.uiEvent
        NewDiaryStepTwoScreen(
            uiEvent = uiEvent.value,
            description = description,
            onDescriptionChanged = { newDiaryStepTwoViewModel.setDescription(it) },
            onBackPressed = onBackPressed,
            onSavedClicked = {
                newDiaryStepTwoViewModel.addNewDiary(
                    Diary().apply {
                        this.description = description
                        this.mood = moods[moodId].name
                    },
                    navigateBack = onBackPressed,
                    onError = { message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }
}
