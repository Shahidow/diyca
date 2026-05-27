package com.example.diyca.feature.home.screens.profile

sealed class ProfileEffect {
    data object NavigateToSettings : ProfileEffect()
    data object NavigateBack : ProfileEffect()
    data object InviteFriends : ProfileEffect()
    data object RateUs : ProfileEffect()
}