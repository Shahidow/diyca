package com.example.diyca.domain.startup.models

import com.example.diyca.domain.home.models.Reward

data class RewardsData(
    val version: Int,
    val rewards: List<Reward>
)