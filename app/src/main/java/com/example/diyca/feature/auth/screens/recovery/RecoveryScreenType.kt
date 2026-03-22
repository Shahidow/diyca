package com.example.diyca.feature.auth.screens.recovery

import com.example.diyca.R

sealed class RecoveryScreenType(
    val previewText: Int,
    val meinText: Int,
    val textFieldLabel: Int,
    val buttonText: Int,
    val isPassword: Boolean,
    val isCode: Boolean
) {
    data object PasswordReset : RecoveryScreenType(
        R.string.password_recovery,
        R.string.enter_email,
        R.string.mail,
        R.string.action_send,
        false,
        false
    )

    data object VerifyResetCode : RecoveryScreenType(
        R.string.check_mail,
        R.string.code_sent,
        R.string.enter_the_received_code,
        R.string.action_confirm,
        false,
        true
    )

    data object ResetPassword : RecoveryScreenType(
        R.string.think_password,
        R.string.another_password,
        R.string.password,
        R.string.update_password,
        true,
        false
    )
}
