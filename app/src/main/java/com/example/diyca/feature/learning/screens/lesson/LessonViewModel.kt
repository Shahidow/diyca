package com.example.diyca.feature.learning.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.R
import com.example.diyca.domain.learning.lesson.LessonInteractor
import com.example.diyca.util.ErrorType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LessonViewModel(private val lessonInteractor: LessonInteractor) : ViewModel() {
    private val _state = MutableStateFlow(LessonState())
    val state = _state.asStateFlow()

    private val _effects = Channel<LessonEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: LessonMsg) {
        when (msg) {
            is LessonMsg.LoadLesson -> {
                val stateData = _state.value
                if (stateData.lessonId == msg.lessonRout.id) return
                _state.update {
                    it.copy(
                        isLoading = true,
                        lessonId = msg.lessonRout.id,
                        topicId = msg.lessonRout.topicId,
                        number = msg.lessonRout.number,
                        title = msg.lessonRout.title,
                        text = msg.lessonRout.text,
                        image = msg.lessonRout.image,
                        audio = msg.lessonRout.audio,
                        tasksCount = msg.lessonRout.tasksCount,
                    )
                }
                viewModelScope.launch {
                    lessonInteractor.getLessonProgress(msg.lessonRout.id, msg.lessonRout.tasksCount)
                        .collect { progress ->
                            _state.update {
                                it.copy(
                                    progress = progress,
                                    isLoading = false
                                )
                            }
                        }
                }
            }

            is LessonMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(LessonEffect.NavigateBack)
                }
            }

            is LessonMsg.StartTasks -> {
                viewModelScope.launch {
                    dispatch(LessonMsg.DismissDialog)
                    val state = _state.value
                    _effects.send(
                        LessonEffect.NavigateToTasks(
                            state.topicId,
                            state.lessonId,
                            msg.isContinue,
                            state.tasksCount
                        )
                    )
                }
            }

            is LessonMsg.StartTasksClicked -> {
                val stateData = _state.value
                when (stateData.progress) {
                    0f -> dispatch(LessonMsg.StartTasks(false))
                    1f -> dispatch(LessonMsg.StartTasks(false))
                    else -> _state.update { it.copy(showConfirmation = true) }
                }
            }

            is LessonMsg.DismissDialog -> _state.update { it.copy(showConfirmation = false) }
            is LessonMsg.Error -> {
                val errorMessage = when (msg.errorType) {
                    ErrorType.NetworkError -> R.string.no_internet
                    ErrorType.ServerError -> R.string.server_error
                    ErrorType.Unauthorized -> R.string.incorrect_credentials
                    else -> R.string.unknown_error
                }
                _state.update { it.copy(error = errorMessage) }
            }
        }
    }
}