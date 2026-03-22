package com.example.diyca.domain.learning.models.task_type

sealed interface UserAnswer {
    data class Sentence(val userWords: List<String>) : UserAnswer
    data class Word(val userLetters: List<String>) : UserAnswer
    data class Choice(val userWord: String) : UserAnswer
}