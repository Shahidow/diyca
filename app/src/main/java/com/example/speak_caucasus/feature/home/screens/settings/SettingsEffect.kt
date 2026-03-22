package com.example.speak_caucasus.feature.home.screens.settings

sealed class SettingsEffect {
    data object NavigateBack: SettingsEffect()
    data object NavigateToLogin: SettingsEffect()
    data class ShowToast(val messageRes: Int): SettingsEffect()
}