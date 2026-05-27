package com.example.diyca.domain.learning.tasks

import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow

interface TasksInteractor {
    fun getTasksList(lessonId: String, isContinue: Boolean): Flow<Resource<List<Task>>>
}