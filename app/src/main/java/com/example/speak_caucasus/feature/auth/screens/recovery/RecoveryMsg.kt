package com.example.speak_caucasus.feature.auth.screens.recovery

import com.example.speak_caucasus.util.ErrorType


sealed class RecoveryMsg {
    data class TextChanged(val text: String, val screenType: RecoveryScreenType) : RecoveryMsg()
    data class Error(val errorType: ErrorType?) : RecoveryMsg()
    object ButtonClicked : RecoveryMsg()
    object ResendCodeClicked : RecoveryMsg()
    object ClickBack : RecoveryMsg()
}