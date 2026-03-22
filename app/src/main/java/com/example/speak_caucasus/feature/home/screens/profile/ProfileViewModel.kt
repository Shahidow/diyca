package com.example.speak_caucasus.feature.home.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.domain.home.profile.ProfileInteractor
import com.example.speak_caucasus.ui.navigation.ScreenRoutes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileInteractor: ProfileInteractor) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _effects = Channel<ProfileEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(ProfileMsg.LoadData)
    }

    fun dispatch(msg: ProfileMsg) {
        when (msg) {
            is ProfileMsg.LoadData -> {
                viewModelScope.launch {
                    val data = profileInteractor.getUserData()
                    viewModelScope.launch {
                        dispatch(
                            ProfileMsg.DataLoaded(
                                pic = data.pic,
                                userName = data.userName,
                                notification = data.notifications
                            )
                        )
                    }
                }

            }

            is ProfileMsg.DataLoaded -> {
                _state.update {
                    it.copy(
                        pic = msg.pic,
                        userName = msg.userName,
                        notifications = msg.notification
                    )
                }
            }

            is ProfileMsg.InviteFriendsClicked -> {
                viewModelScope.launch {
                    _effects.send(ProfileEffect.InviteFriends)
                }
            }

            is ProfileMsg.RateUsClicked -> {
                viewModelScope.launch {
                    _effects.send(ProfileEffect.RateUs)
                }
            }

            is ProfileMsg.GoToSettings -> {
                viewModelScope.launch {
                    _effects.send(ProfileEffect.NavigateTo(ScreenRoutes.SettingsRout))
                }
            }

            is ProfileMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(ProfileEffect.NavigateBack)
                }
            }

            is ProfileMsg.NotificationChange -> {
                _state.update { it.copy(notifications = msg.isEnabled) }
            }
        }
    }
}