package com.example.diyca.feature.learning.screens.study_plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.learning.study_plan.StudyPlanInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudyPlanViewModel(private val studyPlanInteractor: StudyPlanInteractor): ViewModel() {

    private val _state = MutableStateFlow(StudyPlanState())
    val state = _state.asStateFlow()

    private val _effects = Channel<StudyPlanEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(StudyPlanMsg.LoadData)
    }

    fun dispatch(msg: StudyPlanMsg){
        when(msg){
            is StudyPlanMsg.LoadData -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    dispatch(
                        StudyPlanMsg.DataLoaded(
                            lessonsList = studyPlanInteractor.getLessons()
                        )
                    )
                }
            }
            is StudyPlanMsg.DataLoaded -> {
                _state.update { it.copy(
                    isLoading = false,
                    lessonsList = msg.lessonsList
                ) }
            }
            is StudyPlanMsg.InternetError -> {

            }
            is StudyPlanMsg.ServerError -> {

            }
            is StudyPlanMsg.StartLesson -> {
                viewModelScope.launch {
                    _effects.send(StudyPlanEffect.NavigateToLesson(msg.lesson))
                }
            }

            is StudyPlanMsg.BackClicked -> {
                viewModelScope.launch {
                    _effects.send(StudyPlanEffect.NavigateBack)
                }
            }
        }
    }
}