package com.example.diyca.data.dto.user_data

import com.google.gson.annotations.SerializedName

data class RewardsResponse(
    @SerializedName("data")
    val data: List<String>
)