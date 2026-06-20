package com.example.diyca.domain.home.profile.impl

import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.profile.ProfileInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ProfileInteractorImpl(private val userDataBaseRepository: UserDataBaseRepository) :
    ProfileInteractor {
    override fun getUserAvatar(): Flow<String> = userDataBaseRepository.getUserAvatar()
    override fun getUserName(): Flow<String> = userDataBaseRepository.getUserName()
    override suspend fun getRewards(): List<Reward> {
        return userDataBaseRepository.getAllRewards().first()
    }
}