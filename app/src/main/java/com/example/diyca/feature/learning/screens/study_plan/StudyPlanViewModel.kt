package com.example.diyca.feature.learning.screens.study_plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.learning.study_plan.StudyPlanInteractor
import com.example.diyca.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudyPlanViewModel(private val studyPlanInteractor: StudyPlanInteractor) : ViewModel() {

    private val _state = MutableStateFlow(StudyPlanState())
    val state = _state.asStateFlow()

    private val _effects = Channel<StudyPlanEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(StudyPlanMsg.LoadData)
    }

    fun dispatch(msg: StudyPlanMsg) {
        when (msg) {
            is StudyPlanMsg.LoadData -> {
                if (_state.value.isLoading) return
                _state.update { it.copy(isLoading = true, error = null) }
                viewModelScope.launch {
                    studyPlanInteractor.getTopics().collect { resource ->
                        when (resource) {
                            is Resource.Success -> _state.update {
                                it.copy(
                                    isLoading = false,
                                    lessonsList = resource.data.orEmpty()
                                )
                            }

                            is Resource.Error -> dispatch(StudyPlanMsg.Error(resource.errorType))
                        }
                    }
                }
            }

            is StudyPlanMsg.Error -> _state.update {
                it.copy(
                    isLoading = false,
                    error = msg.errorType
                )
            }

            is StudyPlanMsg.StartTopic -> {
                viewModelScope.launch {
                    _effects.send(StudyPlanEffect.NavigateToLesson(msg.topic))
                }
            }
        }
    }
}