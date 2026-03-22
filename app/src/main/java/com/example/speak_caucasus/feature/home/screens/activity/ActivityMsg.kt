package com.example.speak_caucasus.feature.home.screens.activity

sealed class ActivityMsg {
    data object GoBack : ActivityMsg()
    data object GoToActivityCalendar : ActivityMsg()
    data object LoadData: ActivityMsg()
}