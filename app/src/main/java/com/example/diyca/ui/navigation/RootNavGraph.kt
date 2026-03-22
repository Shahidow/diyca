package com.example.diyca.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.diyca.feature.root.AppViewModel

@Composable
fun RootNavGraph(
    appViewModel: AppViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val state by appViewModel.state.collectAsState()

    if (state.isAuthorized == null) {
        // Ждем, пока определится статус авторизации
        // Здесь можно показать заглушку или просто ничего не рисовать, если используется системный SplashScreen
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (state.isAuthorized == true) ScreenRoutes.MainGraph else ScreenRoutes.AuthGraph
    ) {
        authNavGraph(navController)
        mainNavGraph(navController, paddingValues)
    }
}