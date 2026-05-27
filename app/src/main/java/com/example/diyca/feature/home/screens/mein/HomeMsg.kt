package com.example.diyca.feature.home.screens.mein

import com.example.diyca.util.ErrorType

sealed class HomeMsg {
    data class Error(val errorType: ErrorType) : HomeMsg()
    data object GoToProfile : HomeMsg()
    data object StartLesson : HomeMsg()
    data object RetryLessonLoad : HomeMsg()
    data object GoToActivity : HomeMsg()
    data object BackClicked : HomeMsg()
    data object ConfirmExit : HomeMsg()
    data object DismissExitDialog : HomeMsg()
}