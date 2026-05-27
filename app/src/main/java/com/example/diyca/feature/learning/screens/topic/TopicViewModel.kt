package com.example.diyca.feature.learning.screens.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.learning.lesson.LessonInteractor
import com.example.diyca.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopicViewModel(private val lessonInteractor: LessonInteractor) : ViewModel() {

    private val _state = MutableStateFlow(TopicState())
    val state = _state.asStateFlow()

    private val _effects = Channel<TopicEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: TopicMsg) {
        when (msg) {
            is TopicMsg.LoadTopic -> {
                if (_state.value.topicId == msg.topicRout.id && _state.value.lessons.isNotEmpty()) {
                    return
                }
                _state.update {
                    it.copy(
                        isLoading = true,
                        topicId = msg.topicRout.id,
                        title = msg.topicRout.header,
                        text = msg.topicRout.text
                    )
                }
                dispatch(TopicMsg.LoadData)
            }

            is TopicMsg.LoadData -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    lessonInteractor.getLessons(_state.value.topicId).collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        lessons = resource.data.orEmpty()
                                    )
                                }
                            }

                            is Resource.Error -> dispatch(TopicMsg.Error(resource.errorType))
                        }
                    }
                }
            }

            is TopicMsg.StartLesson -> {
                viewModelScope.launch {
                    _effects.send(TopicEffect.NavigateToLesson(msg.lesson))
                }
            }

            is TopicMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(TopicEffect.NavigateBack)
                }
            }

            is TopicMsg.Error -> _state.update { it.copy(isLoading = false, error = msg.errorType) }
        }
    }
}