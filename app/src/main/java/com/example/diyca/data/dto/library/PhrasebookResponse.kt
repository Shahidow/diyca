package com.example.diyca.data.dto.library

import com.google.gson.annotations.SerializedName

data class PhrasebookResponse(
    @SerializedName("version")
    val version: Int,
    @SerializedName("data")
    val data: List<PhrasebookTopicData>
)

data class PhrasebookTopicData(
    @SerializedName("topic")
    val topic: TopicDto,
    @SerializedName("phrases")
    val phrases: List<PhraseDto>
)

data class TopicDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class PhraseDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("translation")
    val translation: List<String>,
    @SerializedName("using_example")
    val usingExample: List<String>?,
    @SerializedName("audio")
    val audio: String?
)