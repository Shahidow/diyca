package com.example.speak_caucasus.domain.home.profile.impl

import com.example.speak_caucasus.R
import com.example.speak_caucasus.domain.home.profile.ProfileInteractor
import com.example.speak_caucasus.domain.home.models.UserProfileData

class ProfileInteractorImpl: ProfileInteractor {
    override fun getUserData(): UserProfileData {
        return UserProfileData(
            pic = R.drawable.ic_avatar_ph,
            userName = "Хасипат",
            notifications = false
        )
    }
}