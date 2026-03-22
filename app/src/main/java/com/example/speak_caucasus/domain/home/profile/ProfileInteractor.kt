package com.example.speak_caucasus.domain.home.profile

import com.example.speak_caucasus.domain.home.models.UserProfileData

interface ProfileInteractor {
    fun getUserData(): UserProfileData
}