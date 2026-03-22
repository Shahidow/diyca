package com.example.diyca.feature.home.screens.activity

sealed class ActivityEffect {
    data object NavigateBack : ActivityEffect()
    data object NavigateToActivityCalendar : ActivityEffect()
}


