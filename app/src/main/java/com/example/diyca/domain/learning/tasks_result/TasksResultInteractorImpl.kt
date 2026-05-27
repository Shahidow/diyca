package com.example.diyca.domain.learning.tasks_result

import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.first

class TasksResultInteractorImpl(
    private val userNetworkRepository: UserNetworkRepository,
    private val userDataBaseRepository: UserDataBaseRepository
) : TasksResultInteractor {
    override suspend fun setProgress(progressList: List<UserProgress>): Resource<Unit> {
        when (val resource = userNetworkRepository.setProgress(progressList)) {
            is Resource.Error -> return Resource.Error(resource.errorType, resource.resultCode)
            is Resource.Success -> {
                progressList.forEach { progress ->
                    userDataBaseRepository.insertUserProgress(progress)
                }
                return Resource.Success(Unit)
            }
        }
    }

    override suspend fun getLessonProgressFloat(lessonId: String, lessonTaskCount: Int): Float {
         val lessonProgress = userDataBaseRepository.getProgressByLessonCount(lessonId).first()
         return lessonProgress.toFloat() / lessonTaskCount.toFloat()
    }
}