package com.example.diyca.data.repository.learning

import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.util.Resource

interface LearningRepository {
    suspend fun getTopics(languageId: String): Resource<List<Topic>>
    suspend fun getLessons(topicId: String): Resource<List<Lesson>>
    suspend fun getTasks(lessonId: String): Resource<List<Task>>
}