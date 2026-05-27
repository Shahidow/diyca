package com.example.diyca.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.diyca.feature.startup.screen.StartupScreen

fun NavGraphBuilder.downloadNavGraph() {
    navigation<ScreenRoutes.DownloadGraph>(
        startDestination = ScreenRoutes.StartupRout
    ) {
        composable<ScreenRoutes.StartupRout> { StartupScreen() }
    }
}