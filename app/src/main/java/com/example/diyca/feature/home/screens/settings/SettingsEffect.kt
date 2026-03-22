package com.example.diyca.feature.home.screens.settings

sealed class SettingsEffect {
    data object NavigateBack: SettingsEffect()
    data object NavigateToLogin: SettingsEffect()
    data class ShowToast(val messageRes: Int): SettingsEffect()
}