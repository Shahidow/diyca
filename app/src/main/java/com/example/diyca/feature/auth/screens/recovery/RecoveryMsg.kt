package com.example.diyca.feature.auth.screens.recovery

import com.example.diyca.util.ErrorType


sealed class RecoveryMsg {
    data class TextChanged(val text: String, val screenType: RecoveryScreenType) : RecoveryMsg()
    data class Error(val errorType: ErrorType?) : RecoveryMsg()
    object ButtonClicked : RecoveryMsg()
    object ResendCodeClicked : RecoveryMsg()
    object ClickBack : RecoveryMsg()
}