package com.example.speak_caucasus.domain.learning.models.task_type

sealed interface Task {
    val id: String
    fun checkAnswer(answer: UserAnswer): Boolean
}



