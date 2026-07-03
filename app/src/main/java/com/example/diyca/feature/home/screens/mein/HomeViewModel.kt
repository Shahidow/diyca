package com.example.diyca.feature.home.screens.mein

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.home.mein.CurrentLessonState
import com.example.diyca.domain.home.mein.HomeInteractor
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.util.LANGUAGE_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class HomeViewModel(private val homeInteractor: HomeInteractor) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effects = Channel<HomeEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        updateRewards()
        observeUserData()
    }

    private fun updateRewards() {
        viewModelScope.launch { homeInteractor.getRewards() }
    }
    private fun observeUserData() {
        viewModelScope.launch {
            launch {
                homeInteractor.getUserAvatar().collect { avatar ->
                    _state.update { it.copy(avatar = UserAvatar.fromKey(avatar)) }
                }
            }
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
                homeInteractor.getUserRewards().collect { rewards ->
                    _state.update { it.copy(rewards = rewards) }
                }
            }
            launch {
                homeInteractor.getLesson(LANGUAGE_ID).collect { currentLessonState ->
                    when (currentLessonState) {
                        is CurrentLessonState.Error -> dispatch(HomeMsg.Error(currentLessonState.errorType))
                        is CurrentLessonState.Active -> _state.update {
                            it.copy(
                                todayLesson = currentLessonState.lesson,
                                todayLessonTopicId = currentLessonState.topicId,
                                isRefreshing = false,
                                isLoading = false,
                                error = null
                            )
                        }

                        is CurrentLessonState.CourseFinished -> _state.update {
                            it.copy(
                                todayLesson = null,
                                isCourseFinished = true,
                                isRefreshing = false,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }

    fun dispatch(msg: HomeMsg) {
        when (msg) {
            is HomeMsg.GoToProfile -> viewModelScope.launch { _effects.send(HomeEffect.GoToProfile) }
            is HomeMsg.StartLesson -> viewModelScope.launch { _effects.send(HomeEffect.StartLesson) }
            is HomeMsg.GoToActivity -> viewModelScope.launch { _effects.send(HomeEffect.GoToActivity) }
            is HomeMsg.BackClicked -> _state.update { it.copy(showConfirmation = true) }
            is HomeMsg.ConfirmExit -> viewModelScope.launch { _effects.send(HomeEffect.CloseApp) }
            is HomeMsg.DismissExitDialog -> _state.update { it.copy(showConfirmation = false) }
            is HomeMsg.RetryLessonLoad -> {
                _state.update { it.copy(isRefreshing = true) }
                homeInteractor.retryGetLesson()
            }

            is HomeMsg.Error -> _state.update {
                it.copy(
                    error = msg.errorType,
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }
}