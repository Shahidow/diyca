package com.example.diyca.domain.startup.models

import com.example.diyca.domain.rewards.models.Reward

data class RewardsData(
    val version: Int,
    val rewards: List<Reward>
)