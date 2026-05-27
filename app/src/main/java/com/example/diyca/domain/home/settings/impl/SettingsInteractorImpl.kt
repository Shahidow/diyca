package com.example.diyca.domain.home.settings.impl

import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.home.settings.SettingsInteractor
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData
import com.example.diyca.domain.session.SessionManager
import com.example.diyca.util.ErrorType
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SettingsInteractorImpl(
    private val authRepository: AuthRepository,
    private val userDataBaseRepository: UserDataBaseRepository,
    private val sessionManager: SessionManager
) : SettingsInteractor {
    override fun getUserAvatar(): Flow<String> = userDataBaseRepository.getUserAvatar()

    override suspend fun insertAvatar(avatar: String) {
        userDataBaseRepository.insertUserAvatar(avatar)
    }

    override fun getUserName(): Flow<String> = userDataBaseRepository.getUserName()

    override suspend fun getUserEmail(): String {
        return userDataBaseRepository.getUserEmail().first()
    }

    override suspend fun insertUserName(userName: String) {
        userDataBaseRepository.insertUserName(userName)
    }

    override suspend fun removeProfile(password: String): Resource<Unit> {
        val token =
            sessionManager.getRefreshToken() ?: return Resource.Error(ErrorType.Unauthorized)
        return authRepository.removeProfile(RemoveProfileData(password, token))
    }

    override suspend fun changeProfile(changeProfileData: ChangeProfileData): Resource<Unit> {
        return authRepository.changeProfile(changeProfileData)
    }

    override suspend fun logout() {
        sessionManager.logout()
        userDataBaseRepository.clearAllData()
    }
}