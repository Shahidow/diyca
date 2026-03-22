package com.example.speak_caucasus.feature.auth.screens.recovery

data class RecoveryState (
    val isLoading: Boolean = false,
    val email: String = "",
    val checkMailCode: String = "",
    val newPassword: String = "",
    val token: String = "",
    val error: Int? = null,
    val screenType: RecoveryScreenType = RecoveryScreenType.PasswordReset,
    val resendTimer: Int = 0,
    val isResendEnabled: Boolean = false
)