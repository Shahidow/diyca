package com.example.speak_caucasus.feature.home.screens.mein

sealed class HomeMsg {
    data class Error(val message: String) : HomeMsg()
    data object GoToProfile : HomeMsg()
    data object StartLesson : HomeMsg()
    data object GoToActivity : HomeMsg()
    data object BackClicked : HomeMsg()
    data object ConfirmExit : HomeMsg()
    data object DismissExitDialog : HomeMsg()
}