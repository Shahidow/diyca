package com.example.diyca.ui.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

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

fun NavController.navigateSafe(route: Any, builder: NavOptionsBuilder.() -> Unit = {}) {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.navigate(route) {
            launchSingleTop = true
            builder()
        }
    }
}

fun NavController.popBackStackSafe() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}

fun NavController.navigateAndClearStack(route: Any) {
    this.navigate(route) {
        popUpTo(this@navigateAndClearStack.graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.navigateAndPopSelf(route: Any) {
    val currentRoute = this.currentBackStackEntry?.destination?.route
    this.navigate(route) {
        if (currentRoute != null) {
            popUpTo(currentRoute) { inclusive = true }
        }
        launchSingleTop = true
    }
}