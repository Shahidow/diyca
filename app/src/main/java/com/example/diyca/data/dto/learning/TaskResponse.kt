package com.example.diyca.data.dto.learning

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("task_type")
    val taskType: String,

    @SerializedName("question_text")
    val questionText: String,

    @SerializedName("options")
    val options: List<String>,

    @SerializedName("answer")
    val answer: List<String>
)
