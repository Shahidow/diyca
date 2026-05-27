package com.example.diyca.feature.auth.screens.registration

sealed class RegistrationEffect {
    data object NavigateToLogin : RegistrationEffect()
    data object OpenPolicyUrl : RegistrationEffect()
}