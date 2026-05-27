package com.example.diyca.feature.home.screens.settings

import com.example.diyca.feature.home.screens.settings.models.SettingsDialog
import com.example.diyca.util.ErrorType

sealed class SettingsMsg {
    data object LoadData : SettingsMsg()
    data object NavigateBack: SettingsMsg()

    data object AvatarPickerClicked : SettingsMsg()
    data object AvatarPickerDismissed : SettingsMsg()
    data class AvatarSelected(val avatarKey: String) : SettingsMsg()

    data class ShowDialog(val dialog: SettingsDialog) : SettingsMsg()
    data object LogOut : SettingsMsg()

    data class ChangePassChanged(val password: String) : SettingsMsg()
    data class UserNameChanged(val text: String) : SettingsMsg()
    data class ConfirmPasswordChanged(val text: String) : SettingsMsg()

    data object PassChangeConfirmed : SettingsMsg()
    data object UserNameChangeConfirmed : SettingsMsg()
    data object RemoveProfileConfirmed : SettingsMsg()
    data object FinalActionConfirmed : SettingsMsg()

    data class Error(val errorType: ErrorType) : SettingsMsg()
    data object DismissDialogs : SettingsMsg()
}