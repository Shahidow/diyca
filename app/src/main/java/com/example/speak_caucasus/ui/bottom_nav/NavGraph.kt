package com.example.speak_caucasus.ui.bottom_nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.speak_caucasus.feature.favorites.screens.favorites.FavoritesScreen
import com.example.speak_caucasus.feature.home.screens.main.HomeScreen
import com.example.speak_caucasus.feature.home.screens.profile.ProfileScreen
import com.example.speak_caucasus.feature.learning.screens.lerning.Learning
import com.example.speak_caucasus.feature.start.screens.login.LoginScreen
import com.example.speak_caucasus.feature.start.screens.registration.Registration

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = ScreenRoutes.LOGIN_SCREEN) {
        composable(ScreenRoutes.HOME_SCREEN) { HomeScreen(navHostController) }
        composable(ScreenRoutes.LEARNING_SCREEN) { Learning() }
        composable(ScreenRoutes.PHRASEBOOK_SCREEN) { Screen3() }
        composable(ScreenRoutes.LIBRARY_SCREEN) { Screen4() }
        composable(ScreenRoutes.FAVORITES_SCREEN) { FavoritesScreen() }

        composable(ScreenRoutes.PROFILE_SCREEN) { ProfileScreen(navHostController) }

        composable(ScreenRoutes.LOGIN_SCREEN) { LoginScreen(navHostController)}
        composable(ScreenRoutes.REGISTRATION_SCREEN) { Registration(navHostController)}
    }
}