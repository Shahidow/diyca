package com.example.diyca.data.dto.library

import com.google.gson.annotations.SerializedName

data class VocabularyResponse(
    @SerializedName("version")
    val version: Int,
    @SerializedName("data")
    val data: List<VocabularyItemDto>
)

data class VocabularyItemDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("word")
    val word: String,
    @SerializedName("translation")
    val translation: List<String>,
    @SerializedName("audio")
    val audio: String?
)
