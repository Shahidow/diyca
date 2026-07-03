package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class UserRewardsResponse(
    @SerializedName("data")
    val data: List<String>
)