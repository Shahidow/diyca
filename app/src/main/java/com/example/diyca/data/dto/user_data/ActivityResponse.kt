package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class ActivityResponse(
    @SerializedName("data")
    val data: List<ActivityItemDto>?,
)

data class ActivityItemDto(
    @SerializedName("dateKey")
    val dateKey: String,
    @SerializedName("tasksCompleted")
    val tasksCompleted: Int,
    @SerializedName("lessonsCompleted")
    val lessonsCompleted: Int
)