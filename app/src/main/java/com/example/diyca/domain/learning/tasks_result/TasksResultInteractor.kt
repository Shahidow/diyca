package com.example.diyca.domain.learning.tasks_result

import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.util.Resource

interface TasksResultInteractor {
    suspend fun setProgress(progressList: List<UserProgress>): Resource<Unit>
    suspend fun getLessonProgressFloat(lessonId: String, lessonTaskCount: Int): Float
}