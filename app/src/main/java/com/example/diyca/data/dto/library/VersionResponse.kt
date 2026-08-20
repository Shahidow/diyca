package com.example.diyca.data.dto.library

import com.google.gson.annotations.SerializedName

data class VersionResponse (
    @SerializedName("version")
    val version: Int
)