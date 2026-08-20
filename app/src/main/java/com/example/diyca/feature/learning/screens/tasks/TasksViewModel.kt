package com.example.diyca.feature.learning.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.learning.models.task_type.BuildSentenceTask
import com.example.diyca.domain.learning.models.task_type.BuildWordTask
import com.example.diyca.domain.learning.models.task_type.MultipleChoiceTask
import com.example.diyca.domain.learning.models.task_type.SingleChoiceTask
import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.domain.learning.models.task_type.UserAnswer
import com.example.diyca.domain.learning.tasks.TasksInteractor
import com.example.diyca.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksInteractor: TasksInteractor) : ViewModel() {
    private val _state = MutableStateFlow(TasksState())
    val state: StateFlow<TasksState> = _state.asStateFlow()

    private val _effects = Channel<TasksEffect>()
    val effects = _effects.receiveAsFlow()

    private var loadJob: Job? = null

    fun dispatch(msg: TasksMsg) {
        when (msg) {
            is TasksMsg.LoadData -> {
                if (_state.value.isLoading || (_state.value.tasks.isNotEmpty() && _state.value.lessonId == msg.tasksRout.lessonId)) return
                loadJob?.cancel()
                _state.update {
                    it.copy(
                        isLoading = true,
                        error = null,
                        topicId = msg.tasksRout.topicId,
                        lessonId = msg.tasksRout.lessonId,
                        lessonTasksCount = msg.tasksRout.lessonTasksCount,
                        topicTasksCount = msg.tasksRout.topicTasksCount
                    )
                }
                loadJob = viewModelScope.launch {
                    tasksInteractor.getTasksList(msg.tasksRout.lessonId, msg.tasksRout.isContinue)
                        .collect { resource ->
                            when (resource) {
                                is Resource.Success -> {
                                    val tasks = resource.data.orEmpty().map { it.shuffled() }
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            tasks = tasks,
                                            tasksListSize = tasks.size,
                                            currentTask = 0,
                                            completedTasks = emptyList(),
                                            answer = null,
                                            selectedLetters = emptyList(),
                                            selectedWords = emptyList(),
                                            selectedSingleWord = "",
                                            selectedMultipleWords = emptyList()
                                        )
                                    }
                                }

                                is Resource.Error -> dispatch(TasksMsg.Error(resource.errorType))
                            }
                        }
                }
            }

            is TasksMsg.Error -> _state.update { it.copy(error = msg.errorType, isLoading = false) }

            is TasksMsg.SelectedLettersChanged -> _state.update { it.copy(selectedLetters = msg.lettersList) }
            is TasksMsg.SelectedWordsChanged -> _state.update { it.copy(selectedWords = msg.wordsList) }
            is TasksMsg.SelectedSingleWordChanged -> {
                _state.update { it.copy(selectedSingleWord = msg.word) }
            }

            is TasksMsg.SelectedMultipleWordsChanged -> {
                _state.update { currentState ->
                    val currentList = currentState.selectedMultipleWords
                    val newList = if (currentList.contains(msg.word)) {
                        currentList - msg.word
                    } else {
                        currentList + msg.word
                    }
                    currentState.copy(selectedMultipleWords = newList)
                }
            }

            is TasksMsg.ActionButtonClicked -> {
                val state = _state.value
                if (state.answer == null) {
                    if (!state.isAnswerNotEmpty) return
                    val task = state.tasks[state.currentTask]
                    val userAnswer = when (task) {
                        is BuildSentenceTask -> UserAnswer.Sentence(state.selectedWords)
                        is BuildWordTask -> UserAnswer.Word(state.selectedLetters)
                        is SingleChoiceTask -> UserAnswer.SingleChoice(state.selectedSingleWord)
                        is MultipleChoiceTask -> UserAnswer.MultipleChoice(state.selectedMultipleWords)
                    }
                    val isCorrect = task.checkAnswer(userAnswer)
                    _state.update {
                        it.copy(
                            answer = isCorrect, completedTasks = if (isCorrect) {
                                it.completedTasks + task.id
                            } else {
                                it.completedTasks
                            }
                        )
                    }
                } else {
                    goToNextTask()
                }
            }

            is TasksMsg.SkipButtonClicked -> goToNextTask()
            is TasksMsg.CloseClicked -> _state.update { it.copy(showCloseConfirmation = true) }
            is TasksMsg.CloseTasks -> {
                _state.update { it.copy(showCloseConfirmation = false) }
                viewModelScope.launch { _effects.send(TasksEffect.CloseTasks) }
            }

            is TasksMsg.DismissDialogs -> _state.update { it.copy(showCloseConfirmation = false) }
        }
    }

    private fun Task.shuffled(): Task {
        return when (this) {
            is BuildSentenceTask -> this.copy(words = words.shuffled())
            is BuildWordTask -> this.copy(letters = letters.shuffled())
            is SingleChoiceTask -> this.copy(options = options.shuffled())
            is MultipleChoiceTask -> this.copy(options = options.shuffled())
        }
    }

    private fun goToNextTask() {
        val currentState = _state.value
        val next = currentState.currentTask + 1
        if (next >= currentState.tasks.size) {
            viewModelScope.launch {
                _effects.send(
                    TasksEffect.NavigateToResult(
                        topicId = currentState.topicId,
                        topicTasksCount = currentState.topicTasksCount,
                        lessonId = currentState.lessonId,
                        completedTasks = currentState.completedTasks,
                        tasksCount = currentState.tasks.size,
                        lessonTasksCount = currentState.lessonTasksCount
                    )
                )
            }
            return
        }
        _state.update {
            it.copy(
                currentTask = next,
                selectedWords = emptyList(),
                selectedSingleWord = "",
                selectedMultipleWords = emptyList(),
                selectedLetters = emptyList(),
                answer = null
            )
        }
    }
}