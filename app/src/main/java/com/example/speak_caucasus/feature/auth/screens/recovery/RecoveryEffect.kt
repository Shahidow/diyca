package com.example.speak_caucasus.feature.auth.screens.recovery

sealed class RecoveryEffect {
    object ClickBack : RecoveryEffect()
    data class ShowToast(val message: String) : RecoveryEffect()
}