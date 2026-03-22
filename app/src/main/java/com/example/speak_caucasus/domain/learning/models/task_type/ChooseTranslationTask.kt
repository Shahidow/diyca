package com.example.speak_caucasus.domain.learning.models.task_type

data class ChooseTranslationTask(
    override val id: String,
    val correctTranslation: String,
    val word: String,
    val options: List<String>
): Task {

    override fun checkAnswer(answer: UserAnswer): Boolean {
        return (answer as? UserAnswer.Choice)?.userWord == correctTranslation
    }
}
