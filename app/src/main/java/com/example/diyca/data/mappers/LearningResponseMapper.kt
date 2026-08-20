package com.example.diyca.data.mappers

import com.example.diyca.data.dto.learning.LessonResponse
import com.example.diyca.data.dto.learning.TaskResponse
import com.example.diyca.data.dto.learning.TopicResponse
import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.domain.learning.models.task_type.BuildSentenceTask
import com.example.diyca.domain.learning.models.task_type.BuildWordTask
import com.example.diyca.domain.learning.models.task_type.MultipleChoiceTask
import com.example.diyca.domain.learning.models.task_type.SingleChoiceTask
import com.example.diyca.domain.learning.models.task_type.Task

class LearningResponseMapper {
    fun topicResponseMapper(topicResponse: TopicResponse): Topic {
        return Topic(
            id = topicResponse.id,
            header = topicResponse.header,
            audio = topicResponse.audio,
            image = topicResponse.image,
            text = topicResponse.text,
            lessonsCount = topicResponse.lessonsCount,
            tasksCount = topicResponse.tasksCount,
        )
    }

    fun lessonResponseMapper(lessonResponse: LessonResponse): Lesson {
        return Lesson(
            id = lessonResponse.id,
            number = lessonResponse.number,
            title = lessonResponse.name,
            text = lessonResponse.text,
            image = lessonResponse.image,
            audio = lessonResponse.audio,
            tasksCount = lessonResponse.tasksCount,
        )
    }

    fun taskResponseMapper(taskResponse: TaskResponse): Task {
        val firstAnswer = taskResponse.answer.firstOrNull() ?: ""
        val cleanOptions = taskResponse.options.filter { it.isNotBlank() }
        return when (taskResponse.taskType) {
            "letter_builder" -> BuildWordTask(
                id = taskResponse.id,
                correctTranslation = firstAnswer,
                question = taskResponse.questionText,
                letters = cleanOptions
            )
            "word_builder" -> BuildSentenceTask(
                id = taskResponse.id,
                correctTranslation = firstAnswer,
                question = taskResponse.questionText,
                words = cleanOptions
            )
            "single_choice" -> SingleChoiceTask(
                id = taskResponse.id,
                correctTranslation = firstAnswer,
                question = taskResponse.questionText,
                options = cleanOptions
            )
            "multiple_choice" -> MultipleChoiceTask(
                id = taskResponse.id,
                correctTranslation = taskResponse.answer,
                question = taskResponse.questionText,
                options = cleanOptions
            )
            else -> throw IllegalArgumentException("Unknown task type: ${taskResponse.taskType}")
        }
    }
}