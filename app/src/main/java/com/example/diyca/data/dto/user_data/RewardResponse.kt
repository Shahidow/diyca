package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class GetAllRewardsResponse(
    @SerializedName("version")
    val version: Int,
    @SerializedName("data")
    val data: List<RewardResponse>
)

data class RewardResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("reward_title")
    val rewardTitle: String,

    @SerializedName("reward_name")
    val rewardName: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("meta")
    val meta: RewardMetaResponse,

    @SerializedName("image_url")
    val imageUrl: String?
)

data class RewardMetaResponse(
    @SerializedName("threshold")
    val threshold: Int
)