package com.example.speak_caucasus.feature.learning.screens.tasks

import androidx.lifecycle.ViewModel
import com.example.speak_caucasus.domain.learning.models.task_type.BuildSentenceTask
import com.example.speak_caucasus.domain.learning.models.task_type.BuildWordTask
import com.example.speak_caucasus.domain.learning.models.task_type.ChooseTranslationTask
import com.example.speak_caucasus.domain.learning.models.task_type.UserAnswer
import com.example.speak_caucasus.domain.learning.tasks.TasksInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class TasksViewModel(private val tasksInteractor: TasksInteractor) : ViewModel() {
    private val _state = MutableStateFlow(TasksState())
    val state: StateFlow<TasksState> = _state.asStateFlow()

    private val _effects = Channel<TasksEffect>()
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(TasksMsg.LoadData)
    }

    fun dispatch(msg: TasksMsg) {
        when (msg) {
            is TasksMsg.LoadData -> {
                _state.update { it.copy(isLoading = true) }
                dispatch(TasksMsg.DataLoaded(tasksInteractor.getTasksList()))
            }

            is TasksMsg.DataLoaded -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        tasks = msg.items,
                        tasksListSize = msg.items.size
                    )
                }
            }

            is TasksMsg.Error -> TODO()
            is TasksMsg.SelectedLettersChanged -> _state.update { it.copy(selectedLetters = msg.lettersList) }
            is TasksMsg.SelectedWordsChanged -> _state.update { it.copy(selectedWords = msg.wordsList) }
            is TasksMsg.SelectedWordChanged -> _state.update { it.copy(selectedWord = msg.word) }
            is TasksMsg.ActionButtonClicked -> {
                val state = _state.value
                if (state.answer == null) {
                    val task = state.tasks[state.currentTask]
                    val userAnswer = when (task) {
                        is BuildSentenceTask ->
                            UserAnswer.Sentence(state.selectedWords)

                        is BuildWordTask ->
                            UserAnswer.Word(state.selectedLetters)

                        is ChooseTranslationTask ->
                            UserAnswer.Choice(state.selectedWord)
                    }
                    val isCorrect = task.checkAnswer(userAnswer)
                    _state.update { it.copy(answer = isCorrect) }
                } else {
                   goToNextTask()
                }
            }
            is TasksMsg.SkipButtonClicked -> goToNextTask()
        }
    }

    private fun goToNextTask() {
        clearData()
        _state.update {
            val next = it.currentTask + 1
            if (next >= it.tasks.size) {
                it // → эффект навигации
            } else {
                it.copy(
                    currentTask = next,
                )
            }
        }
    }

    private fun clearData() {
        _state.update {
            it.copy(
                selectedWords = emptyList(),
                selectedWord = "",
                selectedLetters = emptyList(),
                answer = null
            )
        }
    }
}