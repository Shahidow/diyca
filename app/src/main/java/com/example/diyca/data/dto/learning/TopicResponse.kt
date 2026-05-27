package com.example.diyca.data.dto.learning

import com.google.gson.annotations.SerializedName

data class TopicResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("topic_name")
    val name: String,

    @SerializedName("topic_header")
    val header: String,

    @SerializedName("topic_text")
    val text: String,

    @SerializedName("topic_audio")
    val audio: String?,

    @SerializedName("topic_image")
    val image: String?,

    @SerializedName("topic_lessons_count")
    val lessonsCount: Int,

    @SerializedName("topic_tasks_count")
    val tasksCount: Int
)