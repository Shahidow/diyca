package com.example.diyca.feature.home.screens.activity

sealed class ActivityMsg {
    data object BackClicked : ActivityMsg()
    data object ActivityCalendarClicked : ActivityMsg()
    data object LoadData: ActivityMsg()
}