package com.example.diyca.feature.auth.screens.login

import com.example.diyca.util.ErrorType

sealed class LoginMsg {
    data class EmailChanged(val email: String) : LoginMsg()
    data class PasswordChanged(val password: String) : LoginMsg()
    data class Error(val errorType: ErrorType) : LoginMsg()
    data class LoginClicked(val email: String, val password: String) : LoginMsg()
    data object DataLoaded: LoginMsg()
    data object RegisterClicked : LoginMsg()
    data object ForgottenPasswordClicked : LoginMsg()
}