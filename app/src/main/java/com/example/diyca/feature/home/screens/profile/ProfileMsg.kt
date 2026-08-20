package com.example.diyca.feature.home.screens.profile

sealed class ProfileMsg {
    data object GoToSettings: ProfileMsg()
    data object BackClicked:ProfileMsg()
    data object InviteFriendsClicked : ProfileMsg()
    data object RateUsClicked : ProfileMsg()
}