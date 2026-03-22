package com.example.speak_caucasus.domain.home.settings.impl

import com.example.speak_caucasus.R
import com.example.speak_caucasus.data.repository.auth.AuthRepository
import com.example.speak_caucasus.data.repository.userdata.UserDataRepository
import com.example.speak_caucasus.domain.home.settings.SettingsInteractor
import com.example.speak_caucasus.domain.home.settings.models.ChangeProfileData
import com.example.speak_caucasus.domain.home.settings.models.RemoveProfileData
import com.example.speak_caucasus.domain.home.settings.models.UserSettings
import com.example.speak_caucasus.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Response

class SettingsInteractorImpl(private val authRepository: AuthRepository, private val userDataRepository: UserDataRepository): SettingsInteractor {
    override fun getUserSettings(): UserSettings {
        return UserSettings(
            pic = R.drawable.ic_avatar_ph,
            userName = "Хасипат",
            userEmail = "qwertyuioo@mail.ru",
            targetLanguage = "",
            appLanguage = ""
        )
    }

    override fun getUserName(): Flow<String> = userDataRepository.getUserName()

    override suspend fun getUserEmail(): String {
        return userDataRepository.getUserEmail().first()
    }

    override suspend fun insertUserName(userName: String) {
        userDataRepository.insertUserName(userName)
    }

    override suspend fun removeProfile(removeProfileData: RemoveProfileData): Resource<Unit> {
        return authRepository.removeProfile(removeProfileData).first()
    }

    override suspend fun changeProfile(changeProfileData: ChangeProfileData): Resource<Unit> {
        return authRepository.changeProfile(changeProfileData).first()
    }
}