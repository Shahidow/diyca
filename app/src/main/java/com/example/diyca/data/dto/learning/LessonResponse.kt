package com.example.diyca.data.dto.learning

import com.google.gson.annotations.SerializedName

data class LessonResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("topic")
    val topic: TopicInfo,

    @SerializedName("lesson_number")
    val number: Int,

    @SerializedName("lesson_name")
    val name: String,

    @SerializedName("lesson_text")
    val text: String,

    @SerializedName("lesson_image")
    val image: String?,

    @SerializedName("lesson_audio")
    val audio: String?,

    @SerializedName("lesson_task_counts")
    val tasksCount: Int
)

data class TopicInfo(
    @SerializedName("id")
    val id: String,
)
