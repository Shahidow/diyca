package com.example.diyca.domain.home.profile

import com.example.diyca.domain.rewards.models.Reward
import kotlinx.coroutines.flow.Flow

interface ProfileInteractor {
    fun getUserAvatar(): Flow<String>
    fun getUserName(): Flow<String>
    fun getUserRewards(): Flow<List<Reward>>
}