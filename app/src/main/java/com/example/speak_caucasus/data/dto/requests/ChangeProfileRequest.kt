package com.example.speak_caucasus.data.dto.requests

import com.google.gson.annotations.SerializedName

data class ChangeProfileRequest(
    @SerializedName("newNickname")
    val newNickname: String? = null,

    @SerializedName("currentPassword")
    val currentPassword: String? = null,

    @SerializedName("newPassword")
    val newPassword: String? = null
)
