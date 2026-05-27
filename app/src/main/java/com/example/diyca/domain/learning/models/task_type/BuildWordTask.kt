package com.example.diyca.domain.learning.models.task_type

data class BuildWordTask(
    override val id: String,
    val correctTranslation: String,
    val question: String,
    val letters: List<String>
): Task {

    override fun checkAnswer(answer: UserAnswer): Boolean {
        val userWord = (answer as? UserAnswer.Word)
            ?.userLetters
            ?.joinToString("")
            ?.trim()
            ?: return false

        return userWord.equals(correctTranslation, ignoreCase = true)
    }
}
