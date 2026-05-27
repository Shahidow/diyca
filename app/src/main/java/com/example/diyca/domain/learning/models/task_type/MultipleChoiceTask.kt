package com.example.diyca.domain.learning.models.task_type

data class MultipleChoiceTask(
    override val id: String,
    val correctTranslation: List<String>,
    val question: String,
    val options: List<String>
): Task {
    override fun checkAnswer(answer: UserAnswer): Boolean {
        if (answer !is UserAnswer.MultipleChoice) return false
        return answer.selectedOptions.toSet() == correctTranslation.toSet()
    }
}
