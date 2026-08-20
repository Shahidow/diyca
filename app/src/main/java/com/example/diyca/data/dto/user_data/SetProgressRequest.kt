package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class SetProgressRequest (
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("rewards")
    val rewards: List<String>,
    @SerializedName("progress")
    val progress: SetProgressDataDto
)

data class SetProgressDataDto(
    @SerializedName("ch")
    val chapters: List<SetProgressChapterDto>
)

data class SetProgressChapterDto(
    @SerializedName("topic")
    val topic: String,
    @SerializedName("lesson")
    val lesson: String,
    @SerializedName("tasks")
    val tasks: List<String>
)