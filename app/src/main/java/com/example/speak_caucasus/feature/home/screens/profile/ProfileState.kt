package com.example.speak_caucasus.feature.home.screens.profile

import com.example.speak_caucasus.R

data class ProfileState (
    val pic: Int = R.drawable.ic_avatar_ph,
    val userName: String = "",
    val notifications: Boolean = false,
)