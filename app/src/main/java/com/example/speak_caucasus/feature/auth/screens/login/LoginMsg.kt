package com.example.speak_caucasus.feature.auth.screens.login

import com.example.speak_caucasus.util.ErrorType

sealed class LoginMsg {
    data class EmailChanged(val email: String) : LoginMsg()
    data class PasswordChanged(val password: String) : LoginMsg()
    data class Error(val errorType: ErrorType) : LoginMsg()
    data class LoginClicked(val email: String, val password: String) : LoginMsg()
    object DataLoaded: LoginMsg()
    object RegisterClicked : LoginMsg()
    object ForgottenPasswordClicked : LoginMsg()
}