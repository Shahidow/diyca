package com.example.diyca.feature.auth.screens.login

sealed class LoginEffect {
    object NavigateToRegistration : LoginEffect()
    object NavigateToRecovery : LoginEffect()
    object NavigateToHome : LoginEffect()
}