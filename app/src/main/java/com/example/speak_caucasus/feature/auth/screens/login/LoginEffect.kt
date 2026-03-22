package com.example.speak_caucasus.feature.auth.screens.login

sealed class LoginEffect {
    object NavigateToRegistration : LoginEffect()
    object NavigateToRecovery : LoginEffect()
    object NavigateToHome : LoginEffect()
}