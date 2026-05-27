package com.example.diyca.feature.auth.screens.login

sealed class LoginEffect {
    data object NavigateToRegistration : LoginEffect()
    data object NavigateToRecovery : LoginEffect()
    data object NavigateToHome : LoginEffect()
}