package com.example.diyca.domain.home.profile.impl

import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.rewards.models.Reward
import com.example.diyca.domain.home.profile.ProfileInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ProfileInteractorImpl(private val userDataBaseRepository: UserDataBaseRepository) :
    ProfileInteractor {
    override fun getUserAvatar(): Flow<String> = userDataBaseRepository.getUserAvatar()
    override fun getUserName(): Flow<String> = userDataBaseRepository.getUserName()
    override fun getUserRewards(): Flow<List<Reward>> {
        return combine(
            userDataBaseRepository.getAllRewards(),
            userDataBaseRepository.getUserRewards()
        ) { allRewards, openedTitles ->
            val openedSet = openedTitles.toSet()
            allRewards.map { reward -> reward.copy(isOpen = openedSet.contains(reward.title)) }
        }
    }
}