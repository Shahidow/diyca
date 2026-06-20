package com.example.diyca.feature.home.screens.settings.models

import com.example.diyca.R

sealed class SettingsDialog(
    val title: Int,
    val message: Int,
    val confirmButtonText: Int,
    val dismissButtonText: Int? = null,
    val showTextField: Boolean = false,
    val textFieldLabel: Int? = null,
    val isPassword: Boolean = true,
) {
    data object LogOutDialog : SettingsDialog(
        title = R.string.log_out,
        message = R.string.logout_confirmation_message,
        confirmButtonText = R.string.action_yes,
        dismissButtonText = R.string.action_cancel
        )

    data object ClearProgressDialog : SettingsDialog(
        title = R.string.clear_progress,
        message = R.string.clear_progress_confirmation,
        confirmButtonText = R.string.action_yes,
        dismissButtonText = R.string.action_cancel
    )

    data object DeleteWarningDialog : SettingsDialog(
        title = R.string.warning_title,
        message = R.string.delete_account_confirmation_message,
        confirmButtonText = R.string.action_yes,
        dismissButtonText = R.string.action_cancel
    )

    data object ChangeNameDialog: SettingsDialog(
        title = R.string.username,
        message = R.string.change_username_prompt,
        confirmButtonText = R.string.action_save,
        dismissButtonText = R.string.action_cancel,
        showTextField = true,
        textFieldLabel = R.string.new_username_label,
        isPassword = false
    )

    data object ChangePassDialog: SettingsDialog(
        title = R.string.change_password,
        message = R.string.think_password,
        confirmButtonText = R.string.action_save,
        dismissButtonText = R.string.action_cancel,
        showTextField = true,
        textFieldLabel = R.string.new_password_label,
        isPassword = true
    )

    data object PasswordDialog: SettingsDialog(
        title = R.string.confirmation_title,
        message = R.string.enter_password_prompt,
        confirmButtonText = R.string.action_confirm,
        dismissButtonText = R.string.action_cancel,
        showTextField = true,
        textFieldLabel = R.string.password,
    )
}