package com.example.speak_caucasus.feature.start.screens.login

sealed class LoginEffect {
    object NavigateToHome : LoginEffect()
    object NavigateToRegister : LoginEffect()
}