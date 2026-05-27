package com.example.diyca.feature.home.screens.settings

import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.feature.home.screens.settings.models.SettingsDialog

data class SettingsState(
    val isLoading: Boolean = false,
    val avatar: UserAvatar? = null,
    val userName: String = "",
    val userEmail: String = "",
    val targetLanguage: String = "",
    val appLanguage: String = "",

    val isAvatarPickerVisible: Boolean = false,
    val activeDialog: SettingsDialog? = null,
    val pendingAction: PendingAction = PendingAction.NONE,
    val error: Int? = null,
    val confirmPasswordInput: String = "",
    val changeNameInput: String = "",
    val changePasswordInput: String = "",
)

enum class PendingAction { NONE, DELETE_PROFILE, CHANGE_NAME, CHANGE_PASSWORD }