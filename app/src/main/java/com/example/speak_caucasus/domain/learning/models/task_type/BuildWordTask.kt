package com.example.speak_caucasus.domain.learning.models.task_type

data class BuildWordTask(
    override val id: String,
    val correctTranslation: String,
    val word: String,
    val letters: List<String>
): Task {

    override fun checkAnswer(answer: UserAnswer): Boolean {
        val userWord = (answer as? UserAnswer.Word)
            ?.userLetters
            ?.joinToString("")
            ?: return false

        return userWord == correctTranslation
    }
}
