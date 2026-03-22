package com.example.diyca.feature.home.screens.settings

import com.example.diyca.util.ErrorType

sealed class SettingsMsg {
    data object LoadData : SettingsMsg()
    data class DataLoaded(
        val pic: Int,
        val targetLanguage: String,
        val appLanguage: String
    ) : SettingsMsg()
    data object NavigateBack: SettingsMsg()

    data object LogOutClicked: SettingsMsg()
    data object LogOut : SettingsMsg()

    data object PassChangeClicked: SettingsMsg()
    data class ChangePassChanged(val password: String) : SettingsMsg()
    data object PassChangeConfirmed : SettingsMsg()

    data object UserNameChangeClicked: SettingsMsg()
    data class UserNameChanged(val text: String) : SettingsMsg()
    data object UserNameChangeConfirmed : SettingsMsg()

    data object RemoveProfileClicked: SettingsMsg()
    data class ConfirmPasswordChanged(val text: String) : SettingsMsg()
    data object RemoveProfileConfirmed : SettingsMsg()

    data object FinalActionConfirmed : SettingsMsg()

    data class Error(val errorType: ErrorType) : SettingsMsg()
    data object DismissDialogs : SettingsMsg()
}