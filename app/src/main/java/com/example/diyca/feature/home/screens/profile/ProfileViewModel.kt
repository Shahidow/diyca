package com.example.diyca.feature.home.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.home.profile.ProfileInteractor
import com.example.diyca.domain.home.settings.models.UserAvatar
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
        observeUserData()
    }

    private fun observeUserData() {
        viewModelScope.launch {
            launch {
                profileInteractor.getUserAvatar().collect { avatar ->
                    _state.update { it.copy(avatar = UserAvatar.fromKey(avatar)) }
                }
            }
            launch {
                profileInteractor.getUserName().collect { name ->
                    _state.update { it.copy(userName = name) }
                }
            }
            launch {
                profileInteractor.getUserRewards().collect { userRewards->
                    _state.update { it.copy(rewards = userRewards) }
                }
            }
        }
    }

    fun dispatch(msg: ProfileMsg) {
        when (msg) {
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
                    _effects.send(ProfileEffect.NavigateToSettings)
                }
            }

            is ProfileMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(ProfileEffect.NavigateBack)
                }
            }
        }
    }
}