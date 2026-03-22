package com.example.diyca.domain.home.settings.models

data class UserSettings(
    val pic: Int,
    val userName: String,
    val userEmail: String,
    val targetLanguage: String,
    val appLanguage: String
)
