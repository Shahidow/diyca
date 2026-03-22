package com.example.diyca.feature.learning.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.learning.lesson.LessonInteractor
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

    init {
        dispatch(LessonMsg.LoadData)
    }

    fun dispatch(msg: LessonMsg) {
        when (msg) {
            is LessonMsg.LoadData -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    dispatch(
                        LessonMsg.DataLoaded(
                            lesson = lessonInteractor.getLesson()
                        )
                    )
                }
            }

            is LessonMsg.DataLoaded -> {
                _state.update { it.copy(
                    isLoading = false,
                    lesson = msg.lesson
                ) }
            }

            is LessonMsg.InternetError -> TODO()
            is LessonMsg.ServerError -> TODO()
            is LessonMsg.StartTasks -> {
                viewModelScope.launch {
                    _effects.send(LessonEffect.NavigateToSection(msg.id))
                }
            }
            is LessonMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(LessonEffect.NavigateBack)
                }
            }
        }
    }
}