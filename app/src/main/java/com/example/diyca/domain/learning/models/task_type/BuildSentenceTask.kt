package com.example.diyca.domain.learning.models.task_type

data class BuildSentenceTask(
    override val id: String,
    val correctTranslation: String,
    val sentence: String,
    val words: List<String>
) : Task {

    override fun checkAnswer(answer: UserAnswer): Boolean {
        val userSentence = (answer as? UserAnswer.Sentence)
            ?.userWords
            ?.joinToString(" ")
            ?: return false

        return userSentence == correctTranslation
    }
}
