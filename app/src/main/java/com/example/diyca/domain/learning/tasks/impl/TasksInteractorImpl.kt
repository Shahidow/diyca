package com.example.diyca.domain.learning.tasks.impl

import com.example.diyca.data.repository.learning.LearningRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.domain.learning.tasks.TasksInteractor
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TasksInteractorImpl(
    private val learningRepository: LearningRepository,
    private val userDataBaseRepository: UserDataBaseRepository
    ): TasksInteractor {

    override fun getTasksList(lessonId: String, isContinue: Boolean): Flow<Resource<List<Task>>> = flow {
        val tasksResource = learningRepository.getTasks(lessonId)
        if (tasksResource is Resource.Error) {
            emit(Resource.Error(tasksResource.errorType, tasksResource.resultCode))
            return@flow
        }
        val allTasks = (tasksResource as Resource.Success).data.orEmpty()
        if (isContinue) {
            userDataBaseRepository.getProgressByLesson(lessonId).collect { completedProgress ->
                val completedIds = completedProgress.map { it.taskId }.toSet()
                val filteredTasks = allTasks.filter { it.id !in completedIds }
                emit(Resource.Success(filteredTasks))
            }
        } else {
            emit(Resource.Success(allTasks))
        }
    }
}