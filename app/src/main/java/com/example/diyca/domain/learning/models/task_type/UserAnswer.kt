package com.example.diyca.domain.learning.models.task_type

sealed interface UserAnswer {
    data class Sentence(val userWords: List<String>) : UserAnswer
    data class Word(val userLetters: List<String>) : UserAnswer
    data class SingleChoice(val userWord: String) : UserAnswer
    data class MultipleChoice(val selectedOptions: List<String>) : UserAnswer
}