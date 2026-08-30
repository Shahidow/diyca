package com.example.diyca.feature.learning.screens.tasks_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.R
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.learning.tasks_result.TasksResultInteractor
import com.example.diyca.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksResultViewModel(private val tasksResultInteractor: TasksResultInteractor) : ViewModel() {
    private val _state = MutableStateFlow(TasksResultState())
    val state = _state.asStateFlow()

    private val _effects = Channel<TasksResultEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: TasksResultMsg) {
        when (msg) {
            is TasksResultMsg.LoadTasksResult -> {
                _state.update { it.copy(isLoading = true) }
                val resultData = msg.tasksResultRout
                val progress =
                    (resultData.completedTasks.size.toFloat() / resultData.tasksCount.toFloat())
                val title = when {
                    progress >= 0.9f -> R.string.title_result_excellent
                    progress >= 0.75f -> R.string.title_result_good
                    progress >= 0.5f -> R.string.title_result_fine
                    progress >= 0.25f -> R.string.title_result_bad
                    progress >= 0.0f -> R.string.title_result_nothing
                    else -> null
                }
                _state.update {
                    it.copy(
                        topicId = resultData.topicId,
                        topicTasksCount = resultData.topicTasksCount,
                        lessonId = resultData.lessonId,
                        completedTasks = resultData.completedTasks,
                        tasksCount = resultData.tasksCount,
                        lessonTasksCount = resultData.lessonTasksCount,
                        progress = progress,
                        title = title
                    )
                }
                dispatch(TasksResultMsg.SetResult)
            }

            is TasksResultMsg.SetResult -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    val currentState = _state.value
                    val progressList = currentState.completedTasks.map { taskId ->
                        UserProgress(
                            taskId = taskId,
                            lessonId = currentState.lessonId,
                            topicId = currentState.topicId
                        )
                    }
                    val result = tasksResultInteractor.setProgress(
                        progressList = progressList,
                        lessonId = currentState.lessonId,
                        topicId = currentState.topicId,
                        topicTasksCount = currentState.topicTasksCount,
                        lessonTasksCount = currentState.lessonTasksCount,
                        completedTasks = currentState.completedTasks
                    )
                    when (result) {
                        is Resource.Success -> {
                            val lessonProgress = tasksResultInteractor.getLessonProgressFloat(
                                lessonId = currentState.lessonId,
                                lessonTaskCount = currentState.lessonTasksCount
                            )
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = null,
                                    lessonProgress = lessonProgress,
                                    rewards = result.data ?: emptyList()
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            dispatch(TasksResultMsg.Error(result.errorType))
                        }
                    }
                }
            }

            is TasksResultMsg.StartTasksClicked -> viewModelScope.launch {
                _effects.send(TasksResultEffect.StartTasks(msg.isContinue))
            }

            is TasksResultMsg.CloseClicked -> viewModelScope.launch {
                _effects.send(TasksResultEffect.CloseTasksResult)
            }

            is TasksResultMsg.Error -> _state.update {
                it.copy(error = msg.errorType, isLoading = false)
            }
        }
    }
}