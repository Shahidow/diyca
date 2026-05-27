package com.example.diyca.domain.learning.models.task_type

data class SingleChoiceTask(
    override val id: String,
    val correctTranslation: String,
    val question: String,
    val options: List<String>
): Task {

    override fun checkAnswer(answer: UserAnswer): Boolean {
        return (answer as? UserAnswer.SingleChoice)?.userWord.equals(correctTranslation, ignoreCase = true)
    }
}
