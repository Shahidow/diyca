package com.example.diyca.ui.bottom_nav

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.feature.root.AppViewModel
import com.example.diyca.ui.navigation.RootNavGraph
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavDestination.Companion.hasRoute

@Composable
fun MainScreen(
    appViewModel: AppViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = currentDestination?.let { dest ->
        dest.hasRoute<ScreenRoutes.HomeRout>() ||
                dest.hasRoute<ScreenRoutes.LearningRout>() ||
                dest.hasRoute<ScreenRoutes.PhrasebookRout>() ||
                dest.hasRoute<ScreenRoutes.LibraryRout>() ||
                dest.hasRoute<ScreenRoutes.FavoritesRout>()
    } ?: false

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigation(navController = navController)
            }
        }
    ) { paddingValues ->
        RootNavGraph(
            appViewModel = appViewModel,
            navController = navController,
            paddingValues = paddingValues
        )
    }
}