package com.example.diyca.feature.home.screens.profile

import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.settings.models.UserAvatar


data class ProfileState (
    val avatar: UserAvatar? = null,
    val userName: String = "",
    val rewards: List<Reward> = emptyList(),
)