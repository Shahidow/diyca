package com.example.diyca.data.network

import com.example.diyca.data.dto.learning.LessonResponse
import com.example.diyca.data.dto.learning.TaskResponse
import com.example.diyca.data.dto.learning.TopicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LearningApi {
    @GET("python/languages/{language_id}/topics")
    suspend fun getTopics(@Path("language_id") languageId: String): Response<List<TopicResponse>>

    @GET("python/topics/{topicId}/lessons")
    suspend fun getLessons(@Path("topicId") topicId: String): Response<List<LessonResponse>>

    @GET("python/lessons/{lesson_id}/tasks")
    suspend fun getTasks(@Path("lesson_id") lessonId: String): Response<List<TaskResponse>>
}