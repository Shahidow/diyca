package com.example.diyca.domain.home.profile.impl

import com.example.diyca.R
import com.example.diyca.domain.home.profile.ProfileInteractor
import com.example.diyca.domain.home.models.UserProfileData

class ProfileInteractorImpl: ProfileInteractor {
    override fun getUserData(): UserProfileData {
        return UserProfileData(
            pic = R.drawable.ic_avatar_ph,
            userName = "Хасипат",
            notifications = false
        )
    }
}