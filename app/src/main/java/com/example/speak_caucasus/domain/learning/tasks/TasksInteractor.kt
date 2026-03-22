package com.example.speak_caucasus.domain.learning.tasks

import com.example.speak_caucasus.domain.learning.models.task_type.Task

interface TasksInteractor {
    fun getTasksList(): List<Task>
}