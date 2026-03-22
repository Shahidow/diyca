package com.example.speak_caucasus.ui.navigation

import androidx.navigation.NavController

// Навигация для BottomBar.
fun NavController.navigateToTab(route: Any) {
    this.navigate(route) {
        popUpTo<ScreenRoutes.HomeRout> {
            saveState = true
            inclusive = false
        }
        launchSingleTop = true
        restoreState = true
    }
}

//Навигация с полной очисткой стека.
fun NavController.navigateAndClearStack(route: Any) {
    this.navigate(route) { popUpTo(0) { inclusive = true } }
}

//Навигация к конкретному экрану с возможностью удаления текущего из стека.
fun NavController.navigateSingleTop(route: Any) {
    this.navigate(route) { launchSingleTop = true }
}

//Очистка стека до определенного экрана (не включая его или включая).
fun NavController.popUpToRoute(route: Any, inclusive: Boolean = false) {
    this.popBackStack(route, inclusive)
}