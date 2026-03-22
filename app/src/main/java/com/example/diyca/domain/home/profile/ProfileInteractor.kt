package com.example.diyca.domain.home.profile

import com.example.diyca.domain.home.models.UserProfileData

interface ProfileInteractor {
    fun getUserData(): UserProfileData
}