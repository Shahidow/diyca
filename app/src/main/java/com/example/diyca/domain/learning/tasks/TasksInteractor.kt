package com.example.diyca.domain.learning.tasks

import com.example.diyca.domain.learning.models.task_type.Task

interface TasksInteractor {
    fun getTasksList(): List<Task>
}