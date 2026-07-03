package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class RewardResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("reward_title")
    val rewardTitle: String,

    @SerializedName("reward_name")
    val rewardName: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("image_url")
    val imageUrl: String?
)