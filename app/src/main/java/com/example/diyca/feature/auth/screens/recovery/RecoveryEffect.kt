package com.example.diyca.feature.auth.screens.recovery

sealed class RecoveryEffect {
    object ClickBack : RecoveryEffect()
    data class ShowToast(val message: String) : RecoveryEffect()
}