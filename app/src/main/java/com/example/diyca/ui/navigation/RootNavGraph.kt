package com.example.diyca.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.diyca.feature.root.AppViewModel

@Composable
fun RootNavGraph(
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    val state by appViewModel.state.collectAsState()
    if (state.isAuthorized == null) return

    val startDest =
        if (state.isAuthorized == true) ScreenRoutes.MainGraph else ScreenRoutes.AuthGraph

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}