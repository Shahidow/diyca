package com.example.diyca.feature.home.screens.mein

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.home.mein.HomeInteractor
import com.example.diyca.ui.navigation.ScreenRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(private val homeInteractor: HomeInteractor) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effects = Channel<HomeEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        observeUserData()
    }

    private fun observeUserData() {
        viewModelScope.launch {
            launch {
                homeInteractor.getUserName().collect { name ->
                    _state.update { it.copy(userName = name) }
                }
            }
            launch {
                homeInteractor.getDailyActivity().collect { activity ->
                    _state.update { it.copy(dailyActivity = activity) }
                }
            }
            launch {
                homeInteractor.getRewards().collect { rewards ->
                    _state.update { it.copy(rewards = rewards) }
                }
            }
            launch {
                val lesson = homeInteractor.getLesson()
                _state.update { it.copy(todayLesson = lesson, isLoading = false) }
            }
        }
    }

    fun dispatch(msg: HomeMsg) {
        when (msg) {
            is HomeMsg.Error -> {
                _state.update { it.copy(error = msg.message, isLoading = false) }
                viewModelScope.launch {
                    _effects.send(HomeEffect.ShowToast(msg.message))
                }
            }

            is HomeMsg.GoToProfile -> viewModelScope.launch {
                _effects.send(HomeEffect.NavigateTo(ScreenRoutes.ProfileRout))
            }

            is HomeMsg.StartLesson -> viewModelScope.launch {
                _effects.send(HomeEffect.ShowToast("Начинаем урок!"))
            }

            is HomeMsg.GoToActivity -> viewModelScope.launch {
                _effects.send(HomeEffect.NavigateTo(ScreenRoutes.ActivityRout))
            }

            is HomeMsg.BackClicked -> _state.update { it.copy(showConfirmation = true) }
            is HomeMsg.ConfirmExit -> viewModelScope.launch { _effects.send(HomeEffect.CloseApp) }
            is HomeMsg.DismissExitDialog -> _state.update { it.copy(showConfirmation = false) }
        }
    }
}