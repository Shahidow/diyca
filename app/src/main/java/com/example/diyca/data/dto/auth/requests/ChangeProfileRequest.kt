package com.example.diyca.data.dto.auth.requests

import com.google.gson.annotations.SerializedName

data class ChangeProfileRequest(
    @SerializedName("newNickname")
    val newNickname: String? = null,

    @SerializedName("currentPassword")
    val currentPassword: String? = null,

    @SerializedName("newPassword")
    val newPassword: String? = null
)
