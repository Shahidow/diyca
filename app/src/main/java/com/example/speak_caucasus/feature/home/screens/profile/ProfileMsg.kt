package com.example.speak_caucasus.feature.home.screens.profile

sealed class ProfileMsg {
    object LoadData: ProfileMsg()
    object GoToSettings: ProfileMsg()
    object BackClicked:ProfileMsg()
    object InviteFriendsClicked : ProfileMsg()
    object RateUsClicked : ProfileMsg()
    data class NotificationChange(val isEnabled: Boolean): ProfileMsg()
    data class DataLoaded(
        val pic: Int,
        val userName: String,
        val notification: Boolean
    ): ProfileMsg()
}