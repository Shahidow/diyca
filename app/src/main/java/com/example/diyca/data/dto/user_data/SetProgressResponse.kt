package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class SetProgressResponse(
    @SerializedName("data")
    val data: SetProgressResponseData?,
)

data class SetProgressResponseData(
    @SerializedName("activity")
    val activity: ActivityItemDto,

    @SerializedName("newRewards")
    val newRewards: List<String>
)
