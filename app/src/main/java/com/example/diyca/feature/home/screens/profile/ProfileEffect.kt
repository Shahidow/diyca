package com.example.diyca.feature.home.screens.profile

import com.example.diyca.ui.navigation.ScreenRoutes

sealed class ProfileEffect {
    data class NavigateTo(val route: ScreenRoutes) : ProfileEffect()
    object NavigateBack : ProfileEffect()
    object InviteFriends : ProfileEffect()
    object RateUs : ProfileEffect()
}