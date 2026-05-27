package com.example.diyca.feature.startup.screen

sealed class StartupMsg {
    data object LoadData : StartupMsg()
}