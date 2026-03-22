package com.example.speak_caucasus.feature.home.screens.settings

import com.example.speak_caucasus.R

data class SettingsState(
    val isLoading: Boolean = false,
    val pic: Int = R.drawable.ic_avatar_ph,
    val userName: String = "",
    val userEmail: String = "",
    val targetLanguage: String = "",
    val appLanguage: String = "",

    // Диалоги
    val pendingAction: PendingAction = PendingAction.NONE,
    val showPasswordDialog: Boolean = false,
    val error: Int? = null,
    val showLogoutDialog: Boolean = false,
    val showDeleteWarningDialog: Boolean = false,
    val confirmPasswordInput: String = "",
    val showChangeNameDialog: Boolean = false,
    val changeNameInput: String = "",
    val showChangePassDialog: Boolean = false,
    val changePasswordInput: String = "",
)

enum class PendingAction { NONE, DELETE_PROFILE, CHANGE_NAME, CHANGE_PASSWORD }