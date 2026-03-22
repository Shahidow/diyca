package com.example.speak_caucasus.feature.home.screens.activity

sealed class ActivityEffect {
    data object NavigateBack : ActivityEffect()
    data object NavigateToActivityCalendar : ActivityEffect()
}


