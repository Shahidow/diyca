package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class ProgressResponse(
    @SerializedName("data")
    val data: ProgressDataDto?,
)

data class ProgressDataDto(
    @SerializedName("ch")
    val chapters: List<ChapterDto>? = emptyList()
)

data class ChapterDto(
    @SerializedName("topic")
    val topic: String,
    @SerializedName("lesson")
    val lesson: String,
    @SerializedName("tasks")
    val tasks: List<String>
)