package com.example.speak_caucasus.ui.bottom_nav

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val screensWithBottomBar = listOf(
        ScreenRoutes.HOME_SCREEN,
        ScreenRoutes.LEARNING_SCREEN,
        ScreenRoutes.PHRASEBOOK_SCREEN,
        ScreenRoutes.LIBRARY_SCREEN,
        ScreenRoutes.FAVORITES_SCREEN,
    )

    Scaffold (
        bottomBar = {
            if (currentRoute in screensWithBottomBar) {
                BottomNavigation(navController = navController)
            }
        }
    ) {
        NavGraph(navHostController = navController)
    }
}