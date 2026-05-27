package com.example.diyca.feature.auth.screens.registration

import com.example.diyca.util.ErrorType

sealed class RegistrationMsg {
    data class ToggleAgreement(val isAgreed: Boolean) : RegistrationMsg()
    data class NameChanged(val name: String) : RegistrationMsg()
    data class EmailChanged(val email: String) : RegistrationMsg()
    data class PasswordChanged(val password: String) : RegistrationMsg()
    data class Error(val errorType: ErrorType) : RegistrationMsg()
    data class RegistrationClicked(val name: String, val email: String, val password: String) :
        RegistrationMsg()

    data object LoginClicked : RegistrationMsg()
    data object OnPolicyClick : RegistrationMsg()
}