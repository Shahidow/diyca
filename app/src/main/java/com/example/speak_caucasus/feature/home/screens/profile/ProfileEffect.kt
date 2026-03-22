package com.example.speak_caucasus.feature.home.screens.profile

import com.example.speak_caucasus.ui.navigation.ScreenRoutes

sealed class ProfileEffect {
    data class NavigateTo(val route: ScreenRoutes) : ProfileEffect()
    object NavigateBack : ProfileEffect()
    object InviteFriends : ProfileEffect()
    object RateUs : ProfileEffect()
}