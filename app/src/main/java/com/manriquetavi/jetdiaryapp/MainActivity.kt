package com.manriquetavi.jetdiaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.manriquetavi.jetdiaryapp.navigation.Screen
import com.manriquetavi.jetdiaryapp.navigation.SetupNavGraph
import com.manriquetavi.jetdiaryapp.ui.theme.JetDiaryAppTheme
import com.manriquetavi.jetdiaryapp.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {

    private var keepSplashOpened = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        WindowCompat.setDecorFitsSystemWindows(window , false)
        setContent {
            val isDarkTheme =  isSystemInDarkTheme()
            var darkTheme by remember { mutableStateOf(isDarkTheme) }
            JetDiaryAppTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                SetupNavGraph(
                    darkTheme = darkTheme,
                    startDestination = getStartDestination(),
                    navController = navController,
                    onThemeUpDate = { darkTheme = !darkTheme },
                    onDataLoaded = { keepSplashOpened = false },
                )
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.create(APP_ID).currentUser
    return if(user != null && user.loggedIn) Screen.Home.route else Screen.Authentication.route
}