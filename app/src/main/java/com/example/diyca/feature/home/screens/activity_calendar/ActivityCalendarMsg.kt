package com.example.diyca.feature.home.screens.activity_calendar

sealed class ActivityCalendarMsg {
    data object BackClicked : ActivityCalendarMsg()
    data object LoadData : ActivityCalendarMsg()
}