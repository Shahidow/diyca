package com.example.speak_caucasus.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.speak_caucasus.feature.auth.screens.login.LoginScreen
import com.example.speak_caucasus.feature.auth.screens.recovery.RecoveryScreen
import com.example.speak_caucasus.feature.auth.screens.registration.Registration

fun NavGraphBuilder.authNavGraph(
    navHostController: NavHostController
) {
    navigation<ScreenRoutes.AuthGraph>(
        startDestination = ScreenRoutes.LoginRout
    ) {
        composable<ScreenRoutes.LoginRout> { LoginScreen(navHostController) }
        composable<ScreenRoutes.RegistrationRout> { Registration(navHostController) }
        composable<ScreenRoutes.RecoveryRout> { RecoveryScreen(navHostController) }
    }
}