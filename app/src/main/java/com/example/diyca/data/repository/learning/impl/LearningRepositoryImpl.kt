package com.example.diyca.data.repository.learning.impl

import com.example.diyca.data.mappers.LearningResponseMapper
import com.example.diyca.data.network.LearningApi
import com.example.diyca.data.repository.learning.LearningRepository
import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.domain.learning.models.task_type.Task
import com.example.diyca.util.ErrorType
import com.example.diyca.util.Resource
import com.example.diyca.util.handleNetworkError
import com.example.diyca.util.safeApiCall

class LearningRepositoryImpl(
    private val learningApi: LearningApi,
    private val learningResponseMapper: LearningResponseMapper
): LearningRepository {
    override suspend fun getTopics(languageId: String): Resource<List<Topic>> = safeApiCall {
        val response = learningApi.getTopics(languageId)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val topics = body.map { topicResponse ->
                    learningResponseMapper.topicResponseMapper(topicResponse)
                }
                Resource.Success(topics)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun getLessons(topicId: String): Resource<List<Lesson>> = safeApiCall {
        val response = learningApi.getLessons(topicId)
        if (response.isSuccessful){
            response.body()?.let { body ->
                val lessons = body.map { lessonResponse ->
                    learningResponseMapper.lessonResponseMapper(lessonResponse)
                }
                Resource.Success(lessons)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun getTasks(lessonId: String): Resource<List<Task>> = safeApiCall {
        val response = learningApi.getTasks(lessonId)
        if (response.isSuccessful){
            response.body()?.let { body ->
                val tasks = body.map { taskResponse ->
                    learningResponseMapper.taskResponseMapper(taskResponse)
                }
                Resource.Success(tasks)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }
}