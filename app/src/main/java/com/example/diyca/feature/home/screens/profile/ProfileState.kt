package com.example.diyca.feature.home.screens.profile

import com.example.diyca.R

data class ProfileState (
    val pic: Int = R.drawable.ic_avatar_ph,
    val userName: String = "",
    val notifications: Boolean = false,
)