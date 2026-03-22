package com.example.diyca.feature.home.screens.mein

import com.example.diyca.ui.navigation.ScreenRoutes

sealed class HomeEffect {
    data class NavigateTo(val route: ScreenRoutes) : HomeEffect()
    data class ShowToast(val message: String) : HomeEffect()
    data object CloseApp: HomeEffect()
}